package com.djambulat69.gofileclient.ui.token

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.ui.MainActivity

class TokenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_token)

        val editText = findViewById<AppCompatEditText>(R.id.token_edit_text)

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                /*getAccountSharedPreferences().edit {
                    putString(
                        getString(R.string.api_token_pref_key),
                        editText.text?.toString().orEmpty()
                    )
                }*/

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

}
