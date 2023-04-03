package com.puj.stepsfitnessgame.data.network.achievement

import com.puj.stepsfitnessgame.domain.models.achievement.Achievement

class AchievementMapper {

    fun mapAchievementDtoListToAchievementList(list: List<AchievementDto>): List<Achievement> {
        return list.map { mapAchievementDtoToAchievement(it) }
    }

    fun mapAchievementDtoToAchievement(achievementDto: AchievementDto): Achievement {
        return Achievement(
            achievementDto.achievementId,
            achievementDto.achievementName,
            achievementDto.achievementDescription,
            achievementDto.achievementType,
            achievementDto.isCompleted
        )
    }
}