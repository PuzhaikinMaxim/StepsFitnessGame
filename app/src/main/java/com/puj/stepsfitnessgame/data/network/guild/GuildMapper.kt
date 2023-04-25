package com.puj.stepsfitnessgame.data.network.guild

import com.puj.stepsfitnessgame.domain.models.guild.GuildListItem

class GuildMapper {

    fun mapGuildListDtoToGuildList(guildDtoList: List<GuildListItemDto>): List<GuildListItem> {
        val guildList = mutableListOf<GuildListItem>()
        guildDtoList.forEach { guildList.add(mapGuildListItemDtoToGuildListItem(it)) }
        return guildList
    }

    fun mapGuildListItemDtoToGuildListItem(guildListItem: GuildListItemDto): GuildListItem {
        return GuildListItem(
            guildListItem.guildId,
            guildListItem.guildName,
            guildListItem.rank,
            guildListItem.amountOfPlayers,
            guildListItem.isEnterRequested
        )
    }
}