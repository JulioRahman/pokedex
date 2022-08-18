package com.kencur.pokedex.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.kencur.pokedex.R
import com.kencur.pokedex.databinding.ActivityDetailBinding
import com.kencur.pokedex.model.PokemonInfo
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationCompat.onTransformationEndContainerApplyParams
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    @set:Inject
    internal lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory

    @get:VisibleForTesting
    internal val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(detailViewModelFactory, pokemonInfoItem)
    }

    private val pokemonInfoItem: PokemonInfo by bundleNonNull(EXTRA_POKEMON_INFO)

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            pokemonInfo = pokemonInfoItem
            vm = viewModel
        }
    }

    companion object {
        @VisibleForTesting
        internal const val EXTRA_POKEMON_INFO = "EXTRA_POKEMON_INFO"

        fun startActivity(transformationLayout: TransformationLayout, pokemonInfo: PokemonInfo) =
            transformationLayout.context.intentOf<DetailActivity> {
                putExtra(EXTRA_POKEMON_INFO to pokemonInfo)
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}
