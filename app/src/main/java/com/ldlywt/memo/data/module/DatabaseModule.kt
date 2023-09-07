package com.ldlywt.memo.data.module

import android.content.Context
import androidx.room.Room
import com.ldlywt.memo.data.dao.MemoDao
import com.ldlywt.memo.data.dao.MemoTagCrossRefDao
import com.ldlywt.memo.data.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return buildDatabase(context)
    }

    private const val DATABASE_NAME = "notes_database"

    private fun buildDatabase(context: Context) =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideMemoDao(database: AppDatabase): MemoDao {
        return database.menoDao()
    }

    @Provides
    @Singleton
    fun provideTagDao(database: AppDatabase): TagDao {
        return database.tagDao()
    }

    @Provides
    @Singleton
    fun provideCrossDao(database: AppDatabase): MemoTagCrossRefDao {
        return database.crossDao()
    }
}
