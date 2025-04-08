package io.github.sergeyboboshko.cereport.migrations

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.composeentity_ksp.base.CeAutoMigration

@CeAutoMigration(from = 1, to = 2)
class SimpleMigrations:AutoMigrationSpec{

}

@CeAutoMigration(from = 2, to = 3,useSpec = true)
@DeleteColumn(tableName = "details_utility_payment", columnName = "mr")
@DeleteColumn(tableName = "details_utility_charge", columnName = "mr")
class SimpleMigrations3:AutoMigrationSpec{
       @Override
       override fun onPostMigrate(db: SupportSQLiteDatabase) {
           // Invoked once auto migration is done        }     }
       }
}

@CeAutoMigration(from = 3, to = 4)
class SimpleMigrations4:AutoMigrationSpec{

}
@CeAutoMigration(from = 4, to = 5)
class SimpleMigrations5:AutoMigrationSpec{

}

@CeAutoMigration(from = 5, to = 6,useSpec = true)
@DeleteColumn(tableName = "details_utility_payment", columnName = "meterReading")
@DeleteColumn(tableName = "details_utility_charge", columnName = "meterReading")
class SimpleMigrations6:AutoMigrationSpec{
       @Override
       override fun onPostMigrate(db: SupportSQLiteDatabase) {
              // Invoked once auto migration is done        }     }
       }
}

@CeAutoMigration(from = 6, to = 7)
class SimpleMigrations7:AutoMigrationSpec{

}

@CeAutoMigration(from = 7, to = 8)
class SimpleMigrations8:AutoMigrationSpec{

}