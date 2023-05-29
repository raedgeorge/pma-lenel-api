package com.atech.pma.service;

import com.atech.pma.entity.mssql.Badge;

import java.util.List;

/**
 * @author raed abu Sa'da
 * on 20/04/2023
 */
public interface BadgeService {

    Badge getBadgeById(int badgeId);

    List<Badge> getAllBadges();
}
