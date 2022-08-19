package com.kencur.pokedex.ui.adapter

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.kencur.pokedex.R
import com.kencur.pokedex.databinding.ItemPokemonBinding
import com.kencur.pokedex.model.PokemonInfo
import com.kencur.pokedex.ui.home.HomeFragmentDirections
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding

class PokemonAdapter : BindingListAdapter<PokemonInfo, PokemonAdapter.PokemonViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        parent.binding<ItemPokemonBinding>(R.layout.item_pokemon).let(::PokemonViewHolder)

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bindPokemon(getItem(position))

    inner class PokemonViewHolder constructor(
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { view ->
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
                val action =
                    HomeFragmentDirections.actionHomeFragmentToInfoFragment(getItem(position))
                view.findNavController().navigate(action)
            }
        }

        fun bindPokemon(pokemonInfo: PokemonInfo) {
            binding.pokemonInfo = pokemonInfo
            binding.executePendingBindings()
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PokemonInfo>() {

            override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean =
                oldItem == newItem
        }
    }
}
