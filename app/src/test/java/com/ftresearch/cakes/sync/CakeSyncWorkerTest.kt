package com.ftresearch.cakes.sync

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import com.ftresearch.MainCoroutineScopeRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CakeSyncWorkerTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val cakeSynchronizer = mock<CakeSynchronizer>()

    private lateinit var sut: CakeSyncWorker

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        sut = TestListenableWorkerBuilder<CakeSyncWorker>(context)
            .setWorkerFactory(CakeWorkerFactory(cakeSynchronizer))
            .build()
    }

    @Test
    fun `should return success result when sync is successful`() = coroutineScopeRule.runBlockingTest {
        val result = sut.doWork()

        assertEquals(Result.success(), result)
    }

    @Test
    fun `should return failure result when sync fails`() = coroutineScopeRule.runBlockingTest {
        whenever(cakeSynchronizer.sync()).thenThrow(RuntimeException())

        val result = sut.doWork()

        assertEquals(Result.failure(), result)
    }
}
