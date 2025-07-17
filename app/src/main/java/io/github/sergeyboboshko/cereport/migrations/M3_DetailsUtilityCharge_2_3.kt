package io.github.sergeyboboshko.cereport.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.composeentity_ksp.base.CeManualMigration

//@CeManualMigration(version = 3)
public object _M3_DetailsUtilityCharge_2_3 : Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE IF NOT EXISTS details_utility_charge (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, parentId INTEGER NOT NULL, utilityId INTEGER NOT NULL, meterId INTEGER NOT NULL, amount REAL NOT NULL, describe TEXT NOT NULL, FOREIGN KEY (parentId) REFERENCES doc_utility_charge (id) ON DELETE CASCADE)")

  }
}
