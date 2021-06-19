package com.djambulat69.gofileclient.ui.uploadFile

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentUploadFileBinding
import com.djambulat69.gofileclient.utils.MIME_TYPE_ALL
import com.djambulat69.gofileclient.utils.viewBinding
import com.djambulat69.gofileclient.utils.viewModelsFactory

class UploadFileFragment : Fragment(R.layout.fragment_upload_file) {

    private val binding by viewBinding { FragmentUploadFileBinding.bind(requireView()) }

    private val presenter: UploadFileViewModel by viewModelsFactory { UploadFileViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContentLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            }

        binding.uploadFileButton.setOnClickListener { getContentLauncher.launch(MIME_TYPE_ALL) }
    }

}
