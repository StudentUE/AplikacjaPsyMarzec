package com.example.psymarzecv2.dane.zdalne

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedListResponse

    @GET("breed/{breed}/images/random")
    suspend fun getRandomDogImageByBreed(
        @Path("breed") breed: String
    ): DogApiResponse

    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogApiResponse

    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }
}
