package com.atech.pma.mappers;

import com.atech.pma.entity.mysql.CardHolder;
import com.atech.pma.model.CardHolderDTO;
import org.mapstruct.Mapper;

/**
 * @author raed abu Sa'da
 * on 12/04/2023
 */

@Mapper
public interface CardHolderMapper extends EntityMapper<CardHolderDTO, CardHolder>{
}
