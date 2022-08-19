package com.kencur.pokedex.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kencur.pokedex.R
import com.kencur.pokedex.databinding.FragmentFavoritesBinding
import com.kencur.pokedex.ui.adapter.PokemonAdapter
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            adapter = PokemonAdapter(onClick = {
                val action = FavoritesFragmentDirections.actionFavoritesFragmentToInfoFragment(it)
                findNavController().navigate(action)
            })
            vm = viewModel
        }.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoritePokemonList()
    }
}