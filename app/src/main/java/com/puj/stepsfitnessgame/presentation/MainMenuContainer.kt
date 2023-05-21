package com.puj.stepsfitnessgame.presentation

interface MainMenuContainer {

    fun startNewScreen(screenId: Int)

    companion object {
        const val GO_BACK = 99
        const val CHALLENGE_LIST_FRAGMENT_CODE = 100
        const val STATISTICS_FRAGMENT_CODE = 101
        const val GOAL_SELECTION_FRAGMENT_CODE = 102
        const val LEVEL_SELECTION_FRAGMENT_CODE = 103
        const val BACK_TO_CHALLENGE_LIST_CODE = 104
        const val DUEL_SEARCH_FRAGMENT_CODE = 105
        const val DUEL_FIELD_FRAGMENT_CODE = 106
        const val DUEL_STATISTICS_FRAGMENT_CODE = 107
        const val GUILD_FRAGMENT_CODE = 108
        const val GUILD_ENTER_REQUESTS_FRAGMENT_CODE = 109
        const val GUILD_LIST_FRAGMENT_CODE = 110
        const val CHOOSE_GUILD_CHALLENGE_FRAGMENT_CODE = 111
        const val GUILD_CREATION_FRAGMENT_CODE = 112
        const val GUILD_EDITION_FRAGMENT_CODE = 113
    }
}