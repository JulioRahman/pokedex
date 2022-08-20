package com.kencur.pokedex.ui.favorites

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.kencur.pokedex.model.Pokemon
import com.kencur.pokedex.repository.PokedexRepository
import com.skydoves.bindables.BR
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository
) : BindingViewModel() {

    private val _favoritePokemons: MutableStateFlow<List<Pokemon>> =
        MutableStateFlow(emptyList())

    @get:Bindable
    val favoritePokemons: StateFlow<List<Pokemon>> = _favoritePokemons

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    init {
        Timber.d("init FavoritesViewModel")

        getFavoritePokemonList()
    }

    fun getFavoritePokemonList() {
        viewModelScope.launch {
            pokedexRepository.fetchFavoritePokemonList(
                onStart = { isLoading = true },
                onComplete = {
                    isLoading = false
                    notifyPropertyChanged(BR.favoritePokemons)
                }
            ).collect {
                _favoritePokemons.value = it
            }
        }
    }
}