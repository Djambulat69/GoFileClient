package com.djambulat69.gofileclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.djambulat69.gofileclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
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
                R.id.options_menu_item -> openFragment(OptionsFragment())
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
