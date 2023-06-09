package com.atech.pma.entity.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */
@Getter
@Setter
@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "badge_id", unique = true, nullable = false)
    private String badgeId;

    private String role;
    private String password;

    @Column(name = "is_logged_in")
    private Boolean loggedIn;

}
