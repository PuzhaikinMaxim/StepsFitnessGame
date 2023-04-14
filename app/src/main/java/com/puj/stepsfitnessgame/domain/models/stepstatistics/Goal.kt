package com.puj.stepsfitnessgame.domain.models.stepstatistics

data class Goal(
    val amountOfSteps: Int,
    val estimatedKilometers: String,
    val isSelected: Boolean = false
) {
}