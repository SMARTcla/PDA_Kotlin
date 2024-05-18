package cz.cvut.fel.sit.pda.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database class for handling Geld data.
 */
@Database(
    entities = [BankEntity::class, TransactionEntity::class],
    version = 2
)
abstract class GeldDatabase : RoomDatabase() {

    abstract val transactionDao: TransactionDao
    abstract val bankDao: BankDao

    companion object {
        private var instance: GeldDatabase? = null

        /**
         * Retrieves the singleton instance of GeldDatabase.
         *
         * @param context The context.
         * @return The singleton instance of GeldDatabase.
         */
        fun getInstance(context: Context): GeldDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    GeldDatabase::class.java,
                    "pda"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }

}
