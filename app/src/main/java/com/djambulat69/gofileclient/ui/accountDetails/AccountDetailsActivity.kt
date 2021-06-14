package com.djambulat69.gofileclient.ui.accountDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.djambulat69.gofileclient.databinding.ActivityAccountDetailsBinding
import com.djambulat69.gofileclient.utils.viewBinding

class AccountDetailsActivity: AppCompatActivity() {

    private val binding by viewBinding { ActivityAccountDetailsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

}
