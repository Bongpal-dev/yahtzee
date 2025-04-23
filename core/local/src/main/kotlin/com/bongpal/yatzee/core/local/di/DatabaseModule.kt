package com.bongpal.yatzee.core.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bongpal.yatzee.core.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "yatzee_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun providesGameRecordDao(appDatabase: AppDatabase) = appDatabase.gameRecordDao()
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE records ADD COLUMN recordId TEXT")

        val cursor = db.query("SELECT id FROM records")

        while (cursor.moveToNext()) {
            val rowId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val uuid = UUID.randomUUID().toString()

            db.execSQL("UPDATE records SET recordId = '$uuid' WHERE id = $rowId")
        }
        cursor.close()
    }
}