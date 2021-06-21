package com.djambulat69.gofileclient.ui.files.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.network.UploadFileData

class FilesAdapter(diffCallback: FileDiffCallback) : RecyclerView.Adapter<FileViewHolder>() {

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<UploadFileData>
        get() = differ.currentList
        set(newItems) {
            differ.submitList(newItems)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)

        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
