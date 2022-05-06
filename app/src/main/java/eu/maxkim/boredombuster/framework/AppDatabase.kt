package eu.maxkim.boredombuster.framework

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.maxkim.boredombuster.activity.framework.db.ActivityDao
import eu.maxkim.boredombuster.activity.model.Activity

@Database(entities = [Activity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}