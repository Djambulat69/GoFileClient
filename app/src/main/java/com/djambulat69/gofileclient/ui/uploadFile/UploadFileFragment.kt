package com.djambulat69.gofileclient.ui.uploadFile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentUploadFileBinding
import com.djambulat69.gofileclient.utils.*

class UploadFileFragment : Fragment(R.layout.fragment_upload_file) {

    private val binding by viewBinding { FragmentUploadFileBinding.bind(requireView()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContentLauncher = registerGetContentLauncher()

        binding.uploadFileButton.setOnClickListener { getContentLauncher.launch(MIME_TYPE_ALL) }
    }

    private fun registerGetContentLauncher() =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            uri?.let {
                if (requireContext().contentResolver.querySize(uri) > UPLOAD_FILE_SIZE_LIMIT_BYTES) {
                    requireContext().toast(getString(R.string.too_big_file))
                    return@let
                }

                requireContext().startService(
                    Intent(requireContext(), UploadFileService::class.java).apply {
                        putExtra(UploadFileService.FILE_URI_EXTRA, uri)
                    }
                )
            }


        }


}
