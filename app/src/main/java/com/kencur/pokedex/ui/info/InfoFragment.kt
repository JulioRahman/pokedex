package com.kencur.pokedex.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kencur.pokedex.R
import com.kencur.pokedex.databinding.FragmentInfoBinding
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BindingFragment<FragmentInfoBinding>(R.layout.fragment_info) {

    private val viewModel: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            pokemonInfo = InfoFragmentArgs.fromBundle(requireArguments()).pokemonInfo
            vm = viewModel
        }.root
    }
}