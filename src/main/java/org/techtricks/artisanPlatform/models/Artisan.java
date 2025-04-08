package org.techtricks.artisanPlatform.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artisans")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Artisan extends BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "username")
    private String userName;


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "skill")
    private String skill;

    @Column(name = "location")
    private String location;

    @Column(name = "rating")
    private float artisanRating;

    @Column(name = "name")
    private String name;

    private double dailyWage = 500.0;

    private int totalWorkDays = 0;

    private double totalEarnings = 0.0;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Attendance> attendanceRecords = new ArrayList<>();

    public void calculateTotalEarnings() {
        this.totalEarnings = this.totalWorkDays * this.dailyWage;
    }
}
