package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class FakeChallengeRemoteDataSource(token: String): ChallengeRemoteDataSource {

    private val challengeList = arrayListOf(
        Challenge(
            0,"Испытание1",20,800,1000,"5ч 30 мин"
        ),
        Challenge(
            1,"Испытание2",50,1000,1000,"10ч 30 мин"
        ),
        Challenge(
            2,"Испытание3",70,500,1000,"4ч 30 мин"
        ),
        Challenge(
            3,"Испытание4",90,300,1000,"2ч 30 мин"
        ),
        Challenge(
            4, "Испытание5", 20, 800, 1000, "5ч 30 мин",
        )
    )

    private var activeChallenge: Challenge? = Challenge(
        4,
        "Испытание5",
        20,
        800,
        1000,
        "5ч 30 мин",
        isStarted = true,
        isShown = true
    )

    override suspend fun updateChallengeProgress(stepCount: Int) {

    }

    override suspend fun getChallengesListByLevel(challengeLevel: Int): Response<List<Challenge>> {
        val newChallengeList = ArrayList(challengeList.toList())
        if(activeChallenge != null){
            newChallengeList.removeIf { it.id == activeChallenge!!.id }
        }

        return Response.Success(newChallengeList)
    }

    override suspend fun getActiveChallenge(): Challenge? {
        return activeChallenge
    }

    override suspend fun startChallenge(challengeId: Int): Response<Unit> {
        activeChallenge = challengeList.find { it.id == challengeId }?.copy(isStarted = true, isShown = true)
        return Response.Success(Unit)
    }

    override suspend fun cancelActiveChallenge(): Response<Unit> {
        activeChallenge = null
        return Response.Success(Unit)
    }

    override suspend fun endActiveChallenge(): Response<CompletedChallengeDataDto> {
        TODO("Not yet implemented")
    }
}