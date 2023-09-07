package com.ldlywt.memo.data.module

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ldlywt.memo.data.model.Memo
import com.ldlywt.memo.data.dao.MemoDao
import com.ldlywt.memo.data.dao.MemoTagCrossRefDao
import com.ldlywt.memo.data.dao.TagDao
import com.ldlywt.memo.data.model.MemoTagCrossRef
import com.ldlywt.memo.data.model.Tag

@Database(
    entities = [Memo::class, Tag::class, MemoTagCrossRef::class], version = 1, exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menoDao(): MemoDao
    abstract fun tagDao(): TagDao
    abstract fun crossDao(): MemoTagCrossRefDao

    companion object {
        private const val DATABASE_NAME = "notes_database"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}
