package com.example.littlelemon.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


@Entity(tableName = "menu_items")
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_items")
    suspend fun getAll(): List<MenuItemRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MenuItemRoom>)

    @Query("DELETE FROM menu_items")
    suspend fun clearAll(): Int
}

@Database(entities = [MenuItemRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}
