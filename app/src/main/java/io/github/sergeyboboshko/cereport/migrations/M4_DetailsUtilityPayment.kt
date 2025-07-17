package io.github.sergeyboboshko.cereport.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.composeentity_ksp.base.CeManualMigration

//@CeManualMigration(version = 4)
object M4_DetailsUtilityPayment:Migration(3,4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS details_utility_payment (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, parentId INTEGER NOT NULL, utilityId INTEGER NOT NULL, meterId INTEGER NOT NULL, amount REAL NOT NULL, describe TEXT NOT NULL, FOREIGN KEY (parentId) REFERENCES doc_utility_charge (id) ON DELETE CASCADE)")
    }
}