package com.puj.stepsfitnessgame.data.network.duel

import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.player.Player

class DuelMapper {

    fun mapDuelDtoToDuelField(duelDto: DuelDto): DuelField {
        return DuelField(
            duelDto.playerHp,
            duelDto.playerInitialHp,
            duelDto.opponentHp,
            duelDto.opponentInitialHp,
            Player(duelDto.playerName),
            Player(duelDto.opponentName),
            duelDto.isDuelFinished,
            duelDto.isWon
        )
    }
}