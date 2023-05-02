package com.atech.pma.mappers;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 12/04/2023
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto (E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
