package com.djambulat69.gofileclient.ui.files

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentFilesBinding
import com.djambulat69.gofileclient.utils.viewBinding

class FilesFragment : Fragment(R.layout.fragment_files) {

    private val binding: FragmentFilesBinding by viewBinding {
        FragmentFilesBinding.bind(requireView())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
