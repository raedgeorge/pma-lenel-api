package com.atech.pma.entity.mssql;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;


@Data
@Entity
@Table(name = "MMOBJS")
public class EmployeeImage implements Serializable {

    public static final long serialVersionUID = 42L;

    @Id
    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "LNL_BLOB")
    private String image;
}
