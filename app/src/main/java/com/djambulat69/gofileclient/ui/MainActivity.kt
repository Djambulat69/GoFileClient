package com.djambulat69.gofileclient.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.databinding.ActivityMainBinding
import com.djambulat69.gofileclient.ui.files.FilesFragment
import com.djambulat69.gofileclient.ui.uploadFile.UploadFileFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, UploadFileFragment())
                .commit()
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.upload_file_menu_item -> openFragment(UploadFileFragment())
                R.id.my_files_menu_item -> openFragment(FilesFragment())
                else -> throw IllegalStateException("Unknown bottom menu item")
            }
        }
    }

    private fun openFragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        return true
    }
}
