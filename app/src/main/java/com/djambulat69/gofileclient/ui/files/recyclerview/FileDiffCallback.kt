package com.djambulat69.gofileclient.ui.files.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.djambulat69.gofileclient.network.UploadFileData

class FileDiffCallback : DiffUtil.ItemCallback<UploadFileData>() {

    override fun areItemsTheSame(oldItem: UploadFileData, newItem: UploadFileData): Boolean {
        return oldItem.fileId == newItem.fileId
    }

    override fun areContentsTheSame(oldItem: UploadFileData, newItem: UploadFileData): Boolean {
        return oldItem == newItem
    }

}
