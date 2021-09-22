package com.ftresearch.cakes.domain.repository

import com.ftresearch.cakes.database.dao.CakeDao
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.extensions.toCake
import com.ftresearch.cakes.extensions.toCakeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CakeRepository @Inject constructor(private val cakeDao: CakeDao) {

    fun getCakes(): Flow<List<Cake>> {
        return cakeDao
            .getCakes()
            .map { cakeEntities ->
                cakeEntities.map { cakeEntity -> cakeEntity.toCake() }
            }
    }

    fun searchCakes(text: String): List<Cake> {
        return cakeDao
            .searchCakes(text)
            .map { cakeEntity -> cakeEntity.toCake() }
    }

    suspend fun insertCakes(cakes: List<Cake>) {
        val cakeEntities = cakes.map { cake -> cake.toCakeEntity() }
        cakeDao.deleteAllAndInsertCakes(cakeEntities)
    }
}
