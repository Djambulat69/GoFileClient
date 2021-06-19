package com.djambulat69.gofileclient.utils

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> Fragment.viewModelsFactory(
    noinline createViewModel: (() -> VM)? = null
): Lazy<VM> {

    return viewModels(factoryProducer = createViewModel?.let { { createViewModelFactory(it) } })

}

inline fun <reified VM : ViewModel> AppCompatActivity.viewModelsFactory(
    noinline createViewModel: (() -> VM)? = null
): Lazy<VM> {

    return viewModels(factoryProducer = createViewModel?.let { { createViewModelFactory(it) } })

}

fun <VM : ViewModel> createViewModelFactory(createViewModel: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return createViewModel() as T
        }
    }
}
