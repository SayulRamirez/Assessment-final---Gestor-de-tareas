package com.metaphorce.assessment_final.entities;

import com.metaphorce.assessment_final.enums.Role;
import com.metaphorce.assessment_final.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "maternal_surname", length = 30)
    private String maternalSurname;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserStatus status;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Role role;
}
