package com.puj.stepsfitnessgame.data.network.challengelevel

import com.puj.stepsfitnessgame.domain.models.challengelevel.ChallengeLevel

class ChallengeLevelMapper {

    fun mapChallengeLevelDtoListToChallengeLevelList(
        challengeLevelDtoList: List<ChallengeLevelDto>
    ): List<ChallengeLevel> {
        val list = ArrayList<ChallengeLevel>()
        for(item in challengeLevelDtoList){
            list.add(
                mapChallengeLevelDtoToChallengeLevel(item)
            )
        }
        return list
    }

    fun mapChallengeLevelDtoToChallengeLevel(
        challengeLevelDto: ChallengeLevelDto
    ): ChallengeLevel {
        return ChallengeLevel(
            challengeLevelDto.dungeonLevel,
            challengeLevelDto.amountOfChallenges,
            challengeLevelDto.amountOfCompletedChallenges,
            challengeLevelDto.isLocked,
            challengeLevelDto.minimalLevelRequirements
        )
    }
}