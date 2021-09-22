package com.ftresearch.cakes.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ftresearch.MainCoroutineScopeRule
import com.ftresearch.cakes.database.dao.CakeDao
import com.ftresearch.cakes.database.entity.CakeEntity
import com.ftresearch.cakes.presentation.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CakeDaoTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CakeDatabase

    private lateinit var sut: CakeDao

    @Before
    fun setUp() = coroutineScopeRule.runBlockingTest {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, CakeDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        sut = database.cakeDao()
        sut.insertCakes(CAKE_ENTITIES)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun `dao can be initialised`() {
        assertNotNull(sut)
    }

    @Test
    fun `getCakes should return cake entities sorted by title`() = coroutineScopeRule.runBlockingTest {
        val result = sut.getCakes().first()

        assertEquals(CAKE_ENTITIES_SORTED, result)
    }

    @Test
    fun `searchCakes should search by title`() = coroutineScopeRule.runBlockingTest {
        val result = sut.searchCakes(CAKE_ENTITIES[2].title)

        assertEquals(listOf(CAKE_ENTITIES[2]), result)
    }

    @Test
    fun `searchCakes should search by desc`() = coroutineScopeRule.runBlockingTest {
        val result = sut.searchCakes(CAKE_ENTITIES[3].desc)

        assertEquals(listOf(CAKE_ENTITIES[3]), result)
    }

    @Test
    fun `searchCakes should return cake entities sorted by title`() = coroutineScopeRule.runBlockingTest {
        val result = sut.searchCakes(SEARCH)

        assertEquals(SEARCH_RESULTS, result)
    }

    @Test
    fun `deleteAll should remove all cake entities`() = coroutineScopeRule.runBlockingTest {
        sut.deleteAll()

        val result = sut.getCakes().first()

        assertEquals(emptyList<CakeEntity>(), result)
    }

    private companion object {

        val CAKE_ENTITIES = TestData.cakeEntities.mapIndexed { index, cakeEntity -> cakeEntity.copy(id = index + 1) }
        val CAKE_ENTITIES_SORTED = CAKE_ENTITIES.sortedBy { cakeEntity -> cakeEntity.title }

        const val SEARCH = TestData.searchTerm
        val SEARCH_RESULTS = CAKE_ENTITIES_SORTED.filter { cakeEntity -> cakeEntity.title.contains(SEARCH, ignoreCase = true) }
    }
}
