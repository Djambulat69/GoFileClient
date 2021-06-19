package com.djambulat69.gofileclient.ui.accountDetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.djambulat69.gofileclient.databinding.ActivityAccountDetailsBinding
import com.djambulat69.gofileclient.databinding.ErrorLayoutBinding
import com.djambulat69.gofileclient.utils.viewBinding

class AccountDetailsActivity : AppCompatActivity() {

    private val binding by viewBinding { ActivityAccountDetailsBinding.inflate(layoutInflater) }
    private val errorBinding by viewBinding { ErrorLayoutBinding.bind(binding.root) }

    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showError(visible: Boolean) {
        errorBinding.errorText.isVisible = visible
        errorBinding.retryButton.isVisible = visible
    }

    private fun showLoading(visible: Boolean) {
        binding.accountDetailsProgressBar.isVisible = visible
    }

}
