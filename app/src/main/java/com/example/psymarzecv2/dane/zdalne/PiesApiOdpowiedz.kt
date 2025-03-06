package com.example.psymarzecv2.dane.zdalne


data class DogApiResponse(
    val message: String,
    val status: String
)


data class BreedListResponse(
    val message: Map<String, List<String>>,
    val status: String
)
