package com.example.psymarzecv2.ui.motyw.szczegoly

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psymarzecv2.dane.DogRepository
import com.example.psymarzecv2.dane.modele.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dog = MutableStateFlow<Dog?>(null)
    val dog: StateFlow<Dog?> = _dog

    init {
        savedStateHandle.get<Int>("dogId")?.let { dogId ->
            viewModelScope.launch {
                _dog.value = dogRepository.getDogById(dogId)
            }
        }
    }
}
