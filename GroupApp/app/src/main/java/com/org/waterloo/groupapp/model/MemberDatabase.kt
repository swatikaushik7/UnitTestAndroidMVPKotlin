package com.org.waterloo.groupapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Member::class,Group::class], version = 1)
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao() :MemberDao
    abstract fun groupDao() : GroupDao

    companion object{
        @Volatile
        private var instance : MemberDatabase? = null

        fun getDatabaseInstance(context: Context):MemberDatabase?{

            if(instance == null){
                synchronized(MemberDatabase::class.java){
                    if(instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            MemberDatabase::class.java,
                            "member_database")
                            .build()
                        }
                    }
                }
            return instance
        }
    }
}