package com.atech.pma.mappers;

import com.atech.pma.entity.mysql.AppUser;
import com.atech.pma.model.AppUserDTO;
import org.mapstruct.Mapper;

/**
 * @author raed abu Sa'da
 * on 15/04/2023
 */

@Mapper
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser>{
}
