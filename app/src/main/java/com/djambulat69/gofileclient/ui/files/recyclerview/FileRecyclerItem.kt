package com.djambulat69.gofileclient.ui.files.recyclerview

sealed class FileRecyclerItem {
    class Folder : FileRecyclerItem()
    class File : FileRecyclerItem()
}
