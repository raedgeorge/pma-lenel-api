package com.atech.pma.entity.mysql;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;


/**
 * @author raed abu Sa'da
 * on 31/03/2023
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "card_holders")
public class CardHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "employee_id", unique = true, nullable = false)
    private int employeeId;

    @Column(name = "badge_id", unique = true, nullable = false)
    private int badgeId;

    @Column(name = "driving_license_expiry_date")
    private LocalDate drivingLicenseExpiryDate;

    @OneToOne(cascade = {CascadeType.PERSIST,
                         CascadeType.DETACH,
                         CascadeType.MERGE,
                         CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private CardHolderCarInfo cardHolderCarInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardHolder that = (CardHolder) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

