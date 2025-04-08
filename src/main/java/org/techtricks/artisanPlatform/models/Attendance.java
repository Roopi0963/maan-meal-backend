package org.techtricks.artisanPlatform.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private boolean present; // True = Present, False = Absent

    @ManyToOne
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;
}
