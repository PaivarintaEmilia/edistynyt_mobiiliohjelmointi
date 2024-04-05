package com.example.edistynyttoinentunti.login

import android.app.Application


// Yliajetaan sovelluksen onCreate-funktio, jotta saadaan login heti ensimmäiseksi näytölle
class CustomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DbProvider.provide(this)
    }
}
