package com.djambulat69.gofileclient.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingFragmentDelegate<VB : ViewBinding>(
    private val inflateBinding: () -> VB
) :
    ReadOnlyProperty<Fragment, VB>,
    LifecycleObserver {

    private var binding: VB? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)

        binding = binding ?: inflateBinding()
        return binding!!
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clearBinding() {
        binding = null
    }
}

fun <VB : ViewBinding> Fragment.viewBinding(inflateBinding: () -> VB): ViewBindingFragmentDelegate<VB> {
    return ViewBindingFragmentDelegate(inflateBinding)
}
