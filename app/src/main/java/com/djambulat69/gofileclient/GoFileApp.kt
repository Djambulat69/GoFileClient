package com.djambulat69.gofileclient

import android.app.Application
import android.content.Context

class GoFileApp : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: GoFileApp

        fun applicationContext(): Context = instance
    }

}
