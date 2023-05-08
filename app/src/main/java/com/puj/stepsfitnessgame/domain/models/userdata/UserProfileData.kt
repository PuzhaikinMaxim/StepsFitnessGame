package com.puj.stepsfitnessgame.domain.models.userdata

import com.puj.stepsfitnessgame.domain.models.guild.GuildData

data class UserProfileData(
    val userData: UserData,
    val amountOfCompletedChallenges: Int,
    val amountOfAchievements: Int,
    val amountOfSteps: Int,
    val duelsWon: Int,
    val guildName: String?,
    val guildLogoId: Int?
) {
}