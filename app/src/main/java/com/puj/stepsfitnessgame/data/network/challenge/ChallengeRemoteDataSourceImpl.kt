package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.DEFAULT_ERROR_CODE
import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.SERVER_NOT_RESPONDING_CODE
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeRemoteDataSourceImpl(
    private val enterToken: String
): ChallengeRemoteDataSource {

    private val challengeApiService = ServiceFactory.create(ChallengeApiService::class.java)

    private val activeChallengeMapper = ActiveChallengeMapper()

    override suspend fun updateChallengeProgress(stepCount: Int) {
        challengeApiService.updateChallengeProgress(enterToken, stepCount)
    }

    override suspend fun getChallengesListByLevel(challengeLevel: Int): Response<List<Challenge>> {
        return try {
            val challengeList =
                challengeApiService
                    .getChallengesListByLevel(enterToken, challengeLevel)
                    .body()

            println(challengeList)

            Response.Success(challengeList ?: listOf())
        } catch (e: Exception) {
            Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun getActiveChallenge(): Challenge? {
        return try {
            activeChallengeMapper.mapActiveChallengeDtoToChallenge(
                challengeApiService.getActiveChallenge(enterToken).body()
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun startChallenge(challengeId: Int): Response<Unit> {
        return try {
            val response = challengeApiService.startChallenge(enterToken, challengeId)

            if (response.isSuccessful) {
                Response.Success(Unit)
            } else {
                Response.Error(DEFAULT_ERROR_CODE)
            }
        } catch (ex: Exception) {
            Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun cancelActiveChallenge(): Response<Unit> {
        return try {
            val response = challengeApiService.cancelActiveChallenge(enterToken)

            if (response.isSuccessful) {
                Response.Success(Unit)
            } else {
                Response.Error(DEFAULT_ERROR_CODE)
            }
        } catch (ex: Exception) {
            Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun endActiveChallenge(): Response<CompletedChallengeDataDto> {
        return try {
            val response = challengeApiService.endActiveChallenge(enterToken)

            if (response.isSuccessful && response.body() != null) {
                Response.Success(response.body()!!)
            } else {
                Response.Error(DEFAULT_ERROR_CODE)
            }
        } catch (ex: Exception){
            Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }
}