package com.puj.stepsfitnessgame.data.network.duel

import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.player.Player

class DuelMapper {

    fun mapDuelDtoToDuelField(duelDto: DuelDto): DuelField {
        return DuelField(
            duelDto.player.hp,
            duelDto.player.initialHp,
            duelDto.opponent.hp,
            duelDto.opponent.initialHp,
            Player(duelDto.player.name),
            Player(duelDto.opponent.name),
            duelDto.isDuelFinished,
            duelDto.isWon
        )
    }
}