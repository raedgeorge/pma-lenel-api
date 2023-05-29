package com.atech.pma.service.impl;

import com.atech.pma.entity.mssql.Badge;
import com.atech.pma.repository.mssql.BadgeRepository;
import com.atech.pma.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author raed abu Sa'da
 * on 20/04/2023
 */

@Service
@AllArgsConstructor
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    @Override
    public Badge getBadgeById(int badgeId) {

        Optional<Badge> optionalBadge = badgeRepository.findById(badgeId);
        return optionalBadge.orElse(null);
    }

    @Override
    public List<Badge> getAllBadges() {

        return badgeRepository.findAll();
    }
}
