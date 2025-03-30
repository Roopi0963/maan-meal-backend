package org.techtricks.artisanPlatform.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.techtricks.artisanPlatform.dto.OrderDTO;
import org.techtricks.artisanPlatform.exceptions.CartNotFoundException;
import org.techtricks.artisanPlatform.exceptions.OrderNotFoundException;
import org.techtricks.artisanPlatform.exceptions.UserNotFoundException;
import org.techtricks.artisanPlatform.models.*;
import org.techtricks.artisanPlatform.repositories.*;

import java.util.List;
import java.util.Optional;
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

    // Create an order from a user's cart
    @Override
    public OrderDTO placeOrder(Long userId, Long addressId) throws UserNotFoundException, CartNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: "+userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart NOt found with userId: "+userId));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(order.getOrderDate());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalPrice());
        order.setAddress((Address) user.getAddresses());

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
        cartRepository.delete(cart); // Clear cart after order placement

        return modelMapper.map(order, OrderDTO.class);
    }

    // Retrieve order by user
    @Override
    public List<OrderDTO> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }


    // Update order status

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not Found Exceptions with this Id:" + orderId);
        }
        Order order = orderOptional.get();
        order.setStatus(orderStatus);
        orderRepository.save(order);
        return order;

    }



}
