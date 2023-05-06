package com.atech.pma.entity.mysql;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author raed abu Sa'da
 * on 07/05/2023
 */

@Data
@Entity
@Table(name = "excel")
public class Excel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ExcelCellName("Name")
    @Column(name = "full_name")
    private String fullName;

    @ExcelCellName("Amount")
    private int amount;
}
