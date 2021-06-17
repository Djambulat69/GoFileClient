package com.djambulat69.gofileclient.ui.files

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.djambulat69.gofileclient.databinding.ActivityFilesBinding
import com.djambulat69.gofileclient.utils.viewBinding

class FilesActivity : AppCompatActivity() {

    private val binding: ActivityFilesBinding by viewBinding {
        ActivityFilesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}
