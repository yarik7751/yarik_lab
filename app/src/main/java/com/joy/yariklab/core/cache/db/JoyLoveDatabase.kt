package com.joy.yariklab.core.cache.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/*@Database(
    entities = [],
    version = 2,
)*/
abstract class JoyLoveDatabase : RoomDatabase() {

    companion object {
        fun newInstance(context: Context) = Room.databaseBuilder(
            context = context,
            klass = JoyLoveDatabase::class.java,
            name = "currencies_db"
        )
            .addMigrations(
                migrationFrom1To2,
            )
            .build()
    }
}