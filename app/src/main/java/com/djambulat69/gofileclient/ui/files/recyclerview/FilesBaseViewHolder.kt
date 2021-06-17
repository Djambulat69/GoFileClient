package com.djambulat69.gofileclient.ui.files.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class FilesBaseViewHolder<T : FileRecyclerItem>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    open fun bind(item: T) = Unit

}
