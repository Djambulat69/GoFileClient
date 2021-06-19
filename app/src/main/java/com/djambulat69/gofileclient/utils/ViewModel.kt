package com.djambulat69.gofileclient.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> Fragment.viewModelsFactory(crossinline createViewModel: () -> VM): Lazy<VM> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return createViewModel() as T
            }
        }
    }
}
