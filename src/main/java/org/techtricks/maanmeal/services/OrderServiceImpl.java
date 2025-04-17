package org.techtricks.maanmeal.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techtricks.maanmeal.dto.*;
import org.techtricks.maanmeal.exceptions.CartNotFoundException;
import org.techtricks.maanmeal.exceptions.OrderNotFoundException;
import org.techtricks.maanmeal.exceptions.UserNotFoundException;
import org.techtricks.maanmeal.models.*;
import org.techtricks.maanmeal.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                            OrderItemRepository orderItemRepository, UserRepository userRepository,
                            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // ✅ Create an order from a user's cart with Lazy Loading Fixes
    @Transactional
    @Override
    public OrderDTO placeOrder(Long userId, Long addressId) throws UserNotFoundException, CartNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with userId: " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // ✅ Fetch Address properly to avoid LazyInitializationException
        Address address = user.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(order.getOrderDate());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalPrice());
        order.setAddress(address);

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductPrice(cartItem.getProductPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderRepository.save(order);
        cartRepository.delete(cart); // ✅ Clear cart after order placement

        return modelMapper.map(order, OrderDTO.class);
    }


    @Transactional
    @Override
    public List<OrderDTO> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserIdWithOrderItems(userId);

        return orders.stream().map(order -> {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

            // Map order items manually
            orderDTO.setOrderItems(order.getOrderItems().stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                    .collect(Collectors.toList()));

            // Safely set the address string
            if (order.getAddress() != null) {
                orderDTO.setAddress(order.getAddress().getFullAddress());
            } else {
                orderDTO.setAddress("N/A");
            }

            return orderDTO;
        }).collect(Collectors.toList());
    }



    @Transactional
    @Override
    public OrderSummaryDTO getOrderSummary(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderProductDTO> products = order.getOrderItems().stream()
                .map(item -> new OrderProductDTO(
                        item.getProduct().getName(),
                        item.getProduct().getDescription(),
                        item.getProduct().getPrice()
                ))
                .collect(Collectors.toList());

        return OrderSummaryDTO.builder()
                .orderId(order.getId())
                .orderStatus(order.getStatus().toString())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate().toString())

                .userName(order.getUser().getUsername())
                .userEmail(order.getUser().getEmail())

                .addressStreet(order.getAddress().getStreet())
                .addressCity(order.getAddress().getCity())
                .addressState(order.getAddress().getState())
                .addressZip(order.getAddress().getZipCode())
                .addressCountry(order.getAddress().getCountry())

                .products(products)
                .build();

    }

    // ✅ Update order status
    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        order.setStatus(orderStatus);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllWithOrderItems(); // Fetch all orders with items

        return orders.stream().map(order -> {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

            // Map order items manually
            orderDTO.setOrderItems(order.getOrderItems().stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                    .collect(Collectors.toList()));

            // Safely set the address string
            if (order.getAddress() != null) {
                orderDTO.setAddress(order.getAddress().getFullAddress());
            } else {
                orderDTO.setAddress("N/A");
            }

            return orderDTO;
        }).collect(Collectors.toList());
    }



    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());

        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().toString());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setAddress(order.getAddress().getFullAddress());

        return dto;
    }

}
