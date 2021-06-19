package com.djambulat69.gofileclient.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentOptionsBinding
import com.djambulat69.gofileclient.ui.accountDetails.AccountDetailsActivity
import com.djambulat69.gofileclient.utils.viewBinding

class OptionsFragment : Fragment(R.layout.fragment_options) {

    private val binding by viewBinding { FragmentOptionsBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountDetails.setOnClickListener {
            startActivity(
                Intent(requireContext(), AccountDetailsActivity::class.java)
            )
        }
    }
}
