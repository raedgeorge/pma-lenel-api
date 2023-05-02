package com.atech.pma.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */
@Data
@Entity
@Table(name = "EMP")
public class Employee {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "SSNO")
    private int badgeId;
}
