package com.djambulat69.gofileclient.ui.uploadFile

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.djambulat69.gofileclient.databinding.DialogFileOptionsBinding

class FileOptionsDialog : DialogFragment() {

    private val tags: ArrayList<String> = arrayListOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogFileOptionsBinding.inflate(layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                parentFragmentManager.setFragmentResult(
                    RESULT_REQUEST_KEY,
                    bundleOf(
                        FILE_URI_RESULT to requireArguments().getParcelable<Uri>(ARG_FILE_URI) as Uri,
                        FILE_URI_RESULT to binding.fileNameEditText.text.toString(),
                        FOLDER_ID_RESULT to binding.folderIdEditText.text.toString(),
                        PASSWORD_RESULT to binding.passwordEditText.text.toString(),
                        TAGS_RESULT to tags,
                        DESCRIPTION_RESULT to binding.descriptionEditText.text.toString()
                    )
                )
                dialog.dismiss()
            }
            .create()
    }

    companion object {

        fun newInstance(fileUri: Uri): FileOptionsDialog =
            FileOptionsDialog().apply {
                arguments = bundleOf(ARG_FILE_URI to fileUri)
            }

        private const val ARG_FILE_URI = "file uri"

        const val RESULT_REQUEST_KEY = "result request key"

        const val FILE_URI_RESULT = "file uri result"
        const val FILE_NAME_RESULT = "file name result"
        const val FOLDER_ID_RESULT = "folder id result"
        const val PASSWORD_RESULT = "password result"
        const val TAGS_RESULT = "tags result"
        const val DESCRIPTION_RESULT = "description_result"

    }

}
