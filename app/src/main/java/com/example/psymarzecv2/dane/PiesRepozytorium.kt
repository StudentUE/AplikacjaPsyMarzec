package com.example.psymarzecv2.dane

import com.example.psymarzecv2.dane.lokalne.bazadanych.DogEntity
import com.example.psymarzecv2.dane.lokalne.bazadanych.DogEntityDao
import com.example.psymarzecv2.dane.modele.Dog
import com.example.psymarzecv2.dane.zdalne.DogApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random
import android.util.Log

interface DogRepository {
    val dogs: Flow<List<Dog>>
    suspend fun add(name: String)
    suspend fun remove(id: Int)
    suspend fun triggerFav(id: Int)
    suspend fun getDogById(id: Int): Dog?
}

class DefaultDogRepository @Inject constructor(
    private val dogDao: DogEntityDao,
    private val dogApi: DogApi
) : DogRepository {

    override val dogs: Flow<List<Dog>> = dogDao.getSortedDogs().map { items ->
        items.map {
            Dog(
                id = it.uid,
                name = it.name,
                breed = it.breed,
                imageUrl = it.imageUrl,
                isFav = it.isFav
            )
        }
    }

    override suspend fun add(name: String) {
        try {

            val breeds = dogApi.getAllBreeds().message.keys.toList()
            val randomBreed = breeds[Random.nextInt(breeds.size)]


            val imageUrl = dogApi.getRandomDogImageByBreed(randomBreed).message

            Log.d("DogRepository", "Dodawanie nowego psa: $name, rasa: $randomBreed")


            val newDog = DogEntity(
                name = name,
                breed = randomBreed,
                imageUrl = imageUrl,
                isFav = false
            )

            dogDao.insertDog(newDog)

        } catch (e: Exception) {
            Log.e("DogRepository", "Błąd podczas dodawania psa", e)
            throw e
        }
    }

    override suspend fun remove(id: Int) {
        dogDao.removeDog(id)
    }

    override suspend fun triggerFav(id: Int) {
        dogDao.triggerFavDog(id)
    }

    override suspend fun getDogById(id: Int): Dog? {
        return dogDao.getDogById(id)?.let {
            Dog(
                id = it.uid,
                name = it.name,
                breed = it.breed,
                imageUrl = it.imageUrl,
                isFav = it.isFav
            )
        }
    }
}
