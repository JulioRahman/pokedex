package com.kencur.pokedex.ui.main

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.kencur.pokedex.model.PokemonInfo
import com.kencur.pokedex.repository.PokedexRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    private val pokemonInfoListFlow = pokemonFetchingIndex.flatMapLatest { page ->
        pokedexRepository.fetchPokemonInfoList(
            page = page,
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        )
    }

    @get:Bindable
    val pokemonInfoList: List<PokemonInfo> by pokemonInfoListFlow.asBindingProperty(
        viewModelScope,
        emptyList()
    )

    init {
        Timber.d("init MainViewModel")
    }

    @MainThread
    fun fetchNextPokemonList() {
        if (!isLoading) {
            pokemonFetchingIndex.value++
        }
    }
}
