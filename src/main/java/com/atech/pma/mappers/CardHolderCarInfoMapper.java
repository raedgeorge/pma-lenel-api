package com.atech.pma.mappers;

import com.atech.pma.entity.mysql.CardHolderCarInfo;
import com.atech.pma.model.CardHolderCarInfoDTO;
import org.mapstruct.Mapper;

/**
 * @author raed abu Sa'da
 * on 12/04/2023
 */

@Mapper
public interface CardHolderCarInfoMapper extends EntityMapper<CardHolderCarInfoDTO, CardHolderCarInfo>{
}
