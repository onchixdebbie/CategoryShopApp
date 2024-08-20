package com.deborah.shoppinglistapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deborah.shoppinglistapp.room.converters.DateConverter
import com.deborah.shoppinglistapp.room.models.Item
import com.deborah.shoppinglistapp.room.models.ShoppingList
import com.deborah.shoppinglistapp.room.models.Store

//links us to the database
//has to be an abstract class that inherits from the room database
@TypeConverters(
    value = [DateConverter::class]
)
@Database(
    //make sure that all the entities in the database are listed as arrays
    entities = [ShoppingList::class,Item::class,Store::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase: RoomDatabase (){
//method to access the database
    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao

    //creating the database in a threadsafe manner
    //creating the instance of a class without instantiating it
    companion object{
        @Volatile
        var INSTANCE:ShoppingListDatabase? = null
                //context to instantiate the database
                fun getDatabase(context: Context): ShoppingListDatabase{
                    return INSTANCE ?: synchronized(this){
                        val instance = Room.databaseBuilder(
                            context,
                            ShoppingListDatabase::class.java,
                            "shopping_db"
                        ).build()

                        INSTANCE = instance
                        return instance
                    }

                }
    }

}