package io.github.sergeyboboshko.ceppb

import android.app.Application
import android.content.Context

class MyApplication1 : Application() {
    // Можна додати додаткову логіку тут, якщо потрібно
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext  // Ініціалізуємо глобальний Application Context
        //appContext = updatedContext  // Ініціалізуємо глобальний Application Context
        //GlobalContext.init(this)
    }
}
