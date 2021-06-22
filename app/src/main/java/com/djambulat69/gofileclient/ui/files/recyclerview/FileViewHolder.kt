package com.djambulat69.gofileclient.ui.files.recyclerview

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ItemFileBinding
import com.djambulat69.gofileclient.network.UploadFileData
import com.djambulat69.gofileclient.utils.context

class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemFileBinding = ItemFileBinding.bind(itemView)

    fun bind(file: UploadFileData) {
        with(binding) {
            fileName.text = file.fileName
            linkShareButton.setOnClickListener {
                createShareChooser(file.downloadPage)
            }
        }
    }

    private fun createShareChooser(fileDownLoadPage: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.link, fileDownLoadPage))
            type = context.getString(R.string.mime_type_text)
        }

        startActivity(
            context,
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.share_chooser_title)
            ),
            null
        )
    }

}
