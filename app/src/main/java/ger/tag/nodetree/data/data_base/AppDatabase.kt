package ger.tag.nodetree.data.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_NAME = "app_data_base"

@Database(
    version = 1,
    entities = [
        NodeDbModel::class,
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getNodeDao(): NodeDao

    companion object {
        private var db: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME,
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                instance.openHelper.writableDatabase
                return instance
            }
        }
    }
}