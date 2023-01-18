package com.puj.stepsfitnessgame.presentation

interface MainMenuContainer {

    fun startNewScreen(screenId: Int)

    companion object {
        const val CHALLENGE_LIST_FRAGMENT_CODE = 100
        const val STATISTICS_FRAGMENT_CODE = 101
    }
}