package com.djambulat69.gofileclient.ui.accountDetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ActivityAccountDetailsBinding
import com.djambulat69.gofileclient.databinding.ErrorLayoutBinding
import com.djambulat69.gofileclient.utils.viewBinding
import io.reactivex.rxjava3.core.Observable

class AccountDetailsActivity : AppCompatActivity(), AccountDetailsView {

    private val binding by viewBinding { ActivityAccountDetailsBinding.inflate(layoutInflater) }
    private val errorBinding by viewBinding { ErrorLayoutBinding.bind(binding.root) }

    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.bind(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unbind()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun render(state: AccountDetailsState) {
        showLoading(state.isLoading)
        showError(state.error != null)
        if (state.details != null) {
            with(binding) {
                emailText.text = state.details.email
                apiTokenText.text = getString(R.string.api_token_prefix, state.details.token)
                filesCount.text = state.details.filesCount.toString()
                foldersCount.text = state.details.foldersCount.toString()
                totalSizeCount.text = state.details.totalSize.toString()
            }
        }
        binding.detailsGroup.isVisible = state.details != null
    }

    override fun loadDetailsIntent(): Observable<Unit> = Observable.create { emitter ->
        errorBinding.retryButton.setOnClickListener {
            emitter.onNext(Unit)
        }
    }

    private fun showError(visible: Boolean) {
        errorBinding.errorText.isVisible = visible
        errorBinding.retryButton.isVisible = visible
    }

    private fun showLoading(visible: Boolean) {
        binding.accountDetailsProgressBar.isVisible = visible
    }

}
