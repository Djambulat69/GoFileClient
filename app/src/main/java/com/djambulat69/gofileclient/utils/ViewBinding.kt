package com.djambulat69.gofileclient.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingActivityDelegate<VB : ViewBinding>(
    private val inflateBinding: () -> VB
) :
    ReadOnlyProperty<AppCompatActivity, VB>,
    LifecycleObserver {

    private var binding: VB? = null

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): VB {
        thisRef.lifecycle.addObserver(this)

        binding = binding ?: inflateBinding()
        return binding!!
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clearBinding() {
        binding = null
    }
}

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

fun <VB : ViewBinding> AppCompatActivity.viewBinding(inflateBinding: () -> VB): ViewBindingActivityDelegate<VB> {
    return ViewBindingActivityDelegate(inflateBinding)
}

fun <VB : ViewBinding> Fragment.viewBinding(inflateBinding: () -> VB): ViewBindingFragmentDelegate<VB> {
    return ViewBindingFragmentDelegate(inflateBinding)
}


