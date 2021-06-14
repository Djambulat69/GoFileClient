package com.djambulat69.gofileclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.djambulat69.gofileclient.R

fun Context.getAccountSharedPreferences(): SharedPreferences {
    return getSharedPreferences(
        getString(R.string.account_shared_preferences_name),
        Context.MODE_PRIVATE
    )
}
