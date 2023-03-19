package com.puj.stepsfitnessgame.data.network.dungeonlevel

import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

class DungeonLevelMapper {

    fun mapDungeonLevelDtoListToDungeonLevelList(
        dungeonLevelDtoList: List<DungeonLevelDto>
    ): List<DungeonLevel> {
        val list = ArrayList<DungeonLevel>()
        for(item in dungeonLevelDtoList){
            list.add(
                mapDungeonLevelDtoToDungeonLevel(item)
            )
        }
        return list
    }

    fun mapDungeonLevelDtoToDungeonLevel(
        dungeonLevelDto: DungeonLevelDto
    ): DungeonLevel {
        return DungeonLevel(
            dungeonLevelDto.dungeonLevel,
            dungeonLevelDto.amountOfChallenges,
            dungeonLevelDto.amountOfCompletedChallenges,
            dungeonLevelDto.isLocked,
            dungeonLevelDto.minimalLevelRequirements
        )
    }
}