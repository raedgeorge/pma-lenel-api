package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @author raed abu Sa'da
 * on 05/04/2023
 */
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByBrandName(String brandName);

}
