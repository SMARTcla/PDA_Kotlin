package cz.cvut.fel.sit.pda.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.time.LocalDate

@Database(entities = [TransactionEntity::class, BankCardEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun bankCardDao(): BankCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "geld_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class LocalDateConverter {
    @androidx.room.TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @androidx.room.TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }
}
