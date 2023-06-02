package com.atech.pma.mappers;

import com.atech.pma.entity.mysql.Alert;
import com.atech.pma.model.AlertDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AlertsMapper extends EntityMapper<AlertDTO, Alert>{
}
