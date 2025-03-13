package io.github.sergeyboboshko.cereport

import android.app.Application
import android.content.Context
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.LocaleHelper
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager

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
