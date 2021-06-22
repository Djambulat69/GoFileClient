package com.djambulat69.gofileclient.ui.uploadFile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentUploadFileBinding
import com.djambulat69.gofileclient.network.NetworkManager
import com.djambulat69.gofileclient.utils.toast
import com.djambulat69.gofileclient.utils.viewBinding

class UploadFileFragment : Fragment(R.layout.fragment_upload_file) {

    private val binding by viewBinding { FragmentUploadFileBinding.bind(requireView()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContentLauncher = registerGetContentLauncher()

        binding.uploadFileButton.setOnClickListener { getContentLauncher.launch(getString(R.string.mime_type_all)) }

        val sendIntent = requireActivity().intent

        sendIntent.extras?.getParcelable<Uri>(Intent.EXTRA_STREAM)?.let {

            if (NetworkManager.isConnected(requireContext())) {
                uploadFile(it)
                sendIntent.removeExtra(Intent.EXTRA_STREAM)
            } else {
                checkNetworkToast()
            }

        }
    }

    private fun registerGetContentLauncher() =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadFile(it)
            }
        }

    private fun uploadFile(uri: Uri) {
        if (NetworkManager.isConnected(requireContext())) {
            requireContext().startService(
                Intent(requireContext(), UploadFileService::class.java).apply {
                    putExtra(UploadFileService.FILE_URI_EXTRA, uri)
                }
            )
        } else {
            checkNetworkToast()
        }
    }

    private fun checkNetworkToast() {
        requireContext().toast(getString(R.string.check_network))
    }

}
