package com.ftresearch.cakes.database.dao

import androidx.room.*
import com.ftresearch.cakes.database.entity.CakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CakeDao {

    @Query("SELECT * FROM cakes ORDER BY title ASC")
    fun getCakes(): Flow<List<CakeEntity>>

    @Query("SELECT * FROM cakes WHERE (title LIKE '%' || :search || '%') OR (`desc` LIKE '%' || :search || '%') ORDER BY title ASC")
    fun searchCakesByTitle(search: String): List<CakeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCakes(cakes: List<CakeEntity>)

    @Query("DELETE FROM Cakes")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAllAndInsertCakes(cakes: List<CakeEntity>) {
        deleteAll()
        insertCakes(cakes)
    }
}
