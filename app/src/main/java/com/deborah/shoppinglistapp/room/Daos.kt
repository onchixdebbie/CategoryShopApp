package com.deborah.shoppinglistapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.deborah.shoppinglistapp.room.models.Item
import com.deborah.shoppinglistapp.room.models.ShoppingList
import com.deborah.shoppinglistapp.room.models.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao{

    //when inserting an item with the same id, the original one is replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    //when updating the items within the table
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    //getting all the items from the table
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    //getting a specific item from the table
    @Query("SELECT * FROM items WHERE item_id =:itemId")
    fun getItem(itemId:Int):Flow<Item>
}

interface StoreDao{

    //when inserting an store with the same id, the original one is replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(store: Store)

    //when updating the store within the table
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(store: Store)

    @Delete
    suspend fun delete(store: Store)

    //getting all the stores from the table
    @Query("SELECT * FROM stores")
    fun getAllStores(): Flow<List<Store>>

    //getting a specific store from the table
    @Query("SELECT * FROM stores WHERE store_id =:storeId")
    fun getStore(storeId:Int):Flow<Store>
}

//Dao for the shopping list
interface ListDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingList)

    //returning the stores
    //condition to join the two tables (using the ON keyword)
    @Query(
        """
        SELECT * FROM items AS I INNER JOIN shopping_list AS S
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id
    """
    )
    fun getItemsWithStoreAndList():Flow<List<ItemsWithStoreAndList>>

    //Filter according to specific category
    @Query(
        """
        SELECT * FROM items AS I INNER JOIN shopping_list AS S
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id WHERE S.list_id =:listId
    """
    )
    //filter by id
    fun getItemsWithStoreAndListFilteredById(listId:Int)
    :Flow<List<ItemsWithStoreAndList>>

    //method to return a single item
    @Query(
        """
        SELECT * FROM items AS I INNER JOIN shopping_list AS S
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id WHERE I.item_id =:itemId
    """
    )
    //filter by id
    fun getItemWithStoreAndListFilteredById(itemId: Int)
            :Flow<ItemsWithStoreAndList>
}

//class that will aid with the displaying of the items to the UI
data class ItemsWithStoreAndList(
    @Embedded val item: Item,
    @Embedded val shoppingList: ShoppingList,
    @Embedded val store: Store,

)