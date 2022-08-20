package com.kencur.pokedex.ui.info

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kencur.pokedex.model.Pokemon
import com.kencur.pokedex.repository.PokedexRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber

class InfoViewModel @AssistedInject constructor(
    private val pokedexRepository: PokedexRepository,
    @Assisted private val pokemon: Pokemon
) : BindingViewModel() {

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    @Bindable
    fun isFavorite(): Boolean {
        return pokedexRepository.isFavoritePokemon(pokemon.id)
    }

    init {
        Timber.d("init DetailViewModel")
    }

    @get:Bindable
    val onFavoriteClick = View.OnClickListener {
        if (isFavorite()) {
            viewModelScope.launch {
                pokedexRepository.removeFavoritePokemon(pokemon.copy(isFavorite = false))
            }
        } else {
            viewModelScope.launch {
                pokedexRepository.addFavoritePokemon(pokemon.copy(isFavorite = true))
            }
        }
        notifyPropertyChanged(::isFavorite)
    }

    @AssistedFactory
    interface Factory {
        fun create(pokemon: Pokemon): InfoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            pokemon: Pokemon
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(pokemon) as T
            }
        }
    }
}
