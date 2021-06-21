package com.djambulat69.gofileclient.ui.uploadFile

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.FragmentUploadFileBinding
import com.djambulat69.gofileclient.network.FileToUpload
import com.djambulat69.gofileclient.utils.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UploadFileFragment : Fragment(R.layout.fragment_upload_file) {

    private val disposable = CompositeDisposable()

    private val binding by viewBinding { FragmentUploadFileBinding.bind(requireView()) }

    private val viewModel: UploadFileViewModel by viewModelsFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uploadedFiles
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { fileName -> binding.root.snackBar("File $fileName just finished uploading.") }
            .dispatchTo(disposable)


        val getContentLauncher = registerGetContentLauncher()

        binding.uploadFileButton.setOnClickListener { getContentLauncher.launch(MIME_TYPE_ALL) }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.disposeSafe()
    }

    private fun registerGetContentLauncher() =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            val file = FileToUpload(
                fileName = requireContext().contentResolver.queryName(uri).orEmpty(),
                bytes = requireContext().contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                }!!,
                mimeType = requireContext().contentResolver.getType(uri).orEmpty()
            )

            viewModel.uploadFile(file)
        }


}
