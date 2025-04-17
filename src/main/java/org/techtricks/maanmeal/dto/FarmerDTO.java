package org.techtricks.maanmeal.dto;


import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDTO {
    private Long id;
    private String username;
    private String skill;
}
