package com.deborah.shoppinglistapp.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

//the first table named Shopping List
@Entity(tableName = "shopping_list")
data class ShoppingList(

    @ColumnInfo(name = "list_id")
    @PrimaryKey
    val id: Int,
    val name: String,
)

//The second table named Item
@Entity(tableName = "items")
data class Item(

    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val item_name: String,
    val qty: String,

//Merging the two tables together
    val listId: Int,
    val storeIdFk: Int,
    val date: Date,
    val isChecked: Boolean,

    )

//The third table named Store
@Entity(tableName = "stores")
data class Store(
    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

//Joining the store and ShoppingList table
    val listIdFk: Int

)