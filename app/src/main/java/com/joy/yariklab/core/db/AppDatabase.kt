package com.joy.yariklab.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joy.yariklab.core.db.dao.CurrencyDao
import com.joy.yariklab.core.db.dao.RateDao
import com.joy.yariklab.core.db.entity.CurrencyLocal
import com.joy.yariklab.core.db.entity.RateLocal

@Database(
    entities = [
        CurrencyLocal::class,
        RateLocal::class,
    ],
    version = 1,
)
abstract class CurrenciesDatabase : RoomDatabase() {

    companion object {
        fun newInstance(context: Context) = Room.databaseBuilder(
            context = context,
            klass = CurrenciesDatabase::class.java,
            name = "currencies_db"
        ).build()
    }

    abstract fun currencyDao(): CurrencyDao
    abstract fun rateDao(): RateDao
}