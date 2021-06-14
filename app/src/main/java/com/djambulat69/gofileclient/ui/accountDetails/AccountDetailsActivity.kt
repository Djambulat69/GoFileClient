package com.djambulat69.gofileclient.ui.accountDetails

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.djambulat69.gofileclient.databinding.ActivityAccountDetailsBinding
import com.djambulat69.gofileclient.network.AccountDetails
import com.djambulat69.gofileclient.utils.Data
import com.djambulat69.gofileclient.utils.process
import com.djambulat69.gofileclient.utils.viewBinding

class AccountDetailsActivity : AppCompatActivity() {

    private val binding by viewBinding { ActivityAccountDetailsBinding.inflate(layoutInflater) }

    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.accountDetails.observe(this, ::processAccountDetailsData)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun processAccountDetailsData(detailsData: Data<AccountDetails>) {
        detailsData.process(
            { setLoading(true) },
            { details ->
                setLoading(false)
                with(binding) {
                    detailsGroup.isVisible = true
                    emailText.text = details.email
                    apiTokenText.text = details.token
                    filesCount.text = details.filesCount.toString()
                    foldersCount.text = details.foldersCount.toString()
                    totalSizeCount.text = details.totalSize.toString()
                }
            },
            { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show() }
        )
    }

    private fun setLoading(visible: Boolean) {
        binding.detailsGroup.isVisible = !visible
        binding.accountDetailsProgressBar.isVisible = visible
    }

}
