package org.example.majk

import android.app.Application
import org.example.majk.di.initKoin
import org.koin.android.ext.koin.androidContext

class MajkApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MajkApplication)
        }
    }

}