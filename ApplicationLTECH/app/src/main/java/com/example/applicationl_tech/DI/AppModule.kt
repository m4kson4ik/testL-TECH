package com.example.applicationl_tech.DI

import android.app.Application
import androidx.room.Room
import com.example.applicationl_tech.Interface.IRepository
import com.example.applicationl_tech.Repository.Repository
import com.example.applicationl_tech.Room.LTECHUserDateBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDateBase(app : Application) : LTECHUserDateBase
    {
        return Room.databaseBuilder(app, LTECHUserDateBase::class.java, "LTECHUser-db").build()
    }

    @Provides
    fun provideRepository(db : LTECHUserDateBase) : IRepository
    {
        return Repository(db)
    }
}