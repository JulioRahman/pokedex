package com.kencur.pokedex.ui.info

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import timber.log.Timber

class InfoViewModel : BindingViewModel() {

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    init {
        Timber.d("init DetailViewModel")
    }
}
