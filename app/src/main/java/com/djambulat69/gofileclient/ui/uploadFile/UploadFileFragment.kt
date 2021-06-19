package com.djambulat69.gofileclient.ui.uploadFile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.databinding.FragmentUploadFileBinding
import com.djambulat69.gofileclient.utils.viewBinding

class UploadFileFragment : Fragment() {

    private val binding by viewBinding { FragmentUploadFileBinding.inflate(layoutInflater) }

    private val presenter: UploadFileViewModel by viewModelsFactory { UploadFileViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContentLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            }

        binding.uploadFileButton.setOnClickListener { getContentLauncher.launch(MIME_TYPE_ALL) }
    }

}
