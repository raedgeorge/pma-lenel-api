package com.atech.pma.repository.mysql;

import com.atech.pma.entity.mysql.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 22/05/2023
 */
public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    Optional<CarModel> findCarModelByModelName(String modelName);
}
