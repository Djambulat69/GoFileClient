package com.djambulat69.gofileclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.databinding.FragmentOptionsBinding
import com.djambulat69.gofileclient.utils.viewBinding

class OptionsFragment : Fragment() {

    private val binding by viewBinding { FragmentOptionsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
