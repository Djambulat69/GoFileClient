package com.djambulat69.gofileclient.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.databinding.FragmentOptionsBinding
import com.djambulat69.gofileclient.ui.accountDetails.AccountDetailsActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountDetails.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    AccountDetailsActivity::class.java
                )
            )
        }
    }
}
