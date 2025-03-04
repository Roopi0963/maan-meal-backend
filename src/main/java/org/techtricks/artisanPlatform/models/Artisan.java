package org.techtricks.artisanPlatform.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
}
