package com.djambulat69.gofileclient.ui.files.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.djambulat69.gofileclient.databinding.ItemFileBinding
import com.djambulat69.gofileclient.network.UploadFileData

class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemFileBinding = ItemFileBinding.bind(itemView)

    fun bind(file: UploadFileData) {
        with(binding) {
            fileName.text = file.fileName
        }
    }

}
