package io.github.sergeyboboshko.cereport.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.composeentity_ksp.base.DatabaseMigration

//@DatabaseMigration(version = 5)
object M5_AddDetailsPayment: Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE details_utility_payment ADD COLUMN meterReading REAL")
    }

}