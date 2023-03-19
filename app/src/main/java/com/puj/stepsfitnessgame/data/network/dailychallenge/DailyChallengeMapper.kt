package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge

class DailyChallengeMapper {

    fun mapDailyChallengeDtoListToDailyChallengeList(
        list: List<DailyChallengeDto>
    ): List<DailyChallenge> {
        val dailyChallengeList = ArrayList<DailyChallenge>()
        for(elem in list){
            dailyChallengeList.add(mapDailyChallengeDtoToDailyChallenge(elem))
        }
        return dailyChallengeList
    }

    fun mapDailyChallengeDtoToDailyChallenge(dailyChallengeDto: DailyChallengeDto): DailyChallenge {
        return DailyChallenge(
            dailyChallengeDto.amountOfSteps,
            dailyChallengeDto.isCompleted
        )
    }
}