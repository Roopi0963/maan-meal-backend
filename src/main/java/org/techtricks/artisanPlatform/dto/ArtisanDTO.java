package org.techtricks.artisanPlatform.dto;


import jakarta.persistence.Entity;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtisanDTO {
    private Long id;
    private String username;
    private String skill;
}
