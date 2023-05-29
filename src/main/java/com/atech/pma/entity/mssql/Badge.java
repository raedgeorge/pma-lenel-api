package com.atech.pma.entity.mssql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author raed abu Sa'da
 * on 19/04/2023
 */

@Data
@Entity
@Table(name = "BADGE")
public class Badge {

    @Id
    @Column(name = "ID")
    private int badgeId;

    @Column(name = "EMPID")
    private int employeeId;

    @Column(name = "BADGEKEY")
    private int badgeKey;

}
