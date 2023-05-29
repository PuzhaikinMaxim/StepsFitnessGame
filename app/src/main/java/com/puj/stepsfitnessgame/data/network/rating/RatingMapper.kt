package com.puj.stepsfitnessgame.data.network.rating

import com.puj.stepsfitnessgame.domain.models.rating.Rating

class RatingMapper {

    fun mapRatingDtoToRating(
        ratingDto: RatingDto,
        ratingType: Rating.RatingType,
        index: Int
    ): Rating {
        return Rating(
            index,
            ratingDto.playerName,
            ratingDto.playerLevel,
            ratingDto.profileImageId,
            ratingDto.place,
            ratingDto.amountOfSteps,
            ratingDto.amountOfDuelsWon,
            ratingType
        )
    }

    fun mapRatingDtoListToRatingList(
        list: List<RatingDto>,
        ratingType: Rating.RatingType
    ): List<Rating> {
        return list.mapIndexed { index, ratingDto ->  mapRatingDtoToRating(
            ratingDto,
            ratingType,
            index
        )}
    }
}