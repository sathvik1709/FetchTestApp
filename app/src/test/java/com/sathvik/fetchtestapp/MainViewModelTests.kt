package com.sathvik.fetchtestapp

import app.cash.turbine.test
import com.sathvik.fetchtestapp.fakes.FakeMainRepository
import com.sathvik.fetchtestapp.ui.viewmodel.MainUIState
import com.sathvik.fetchtestapp.ui.viewmodel.MainViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MainViewModelTests {

    private val repository = FakeMainRepository()

    @Test
    fun testGetDataSuccess() = runTest {
        repository.shouldResultSucceed = true

        val sut = MainViewModel(repository)
        sut.fetchData()


        sut.uiState.test {
            val initState = awaitItem()
            assertTrue(initState is MainUIState.Init)


            val dataState = awaitItem()
            assertTrue(dataState is MainUIState.Data)
            assertEquals((dataState as MainUIState.Data).list.size, 4)
        }
    }

    @Test
    fun testGetDataFailure() = runTest {
        repository.shouldResultSucceed = false

        val sut = MainViewModel(repository)
        sut.fetchData()

        sut.uiState.test {
            val initState = awaitItem()
            assertTrue(initState is MainUIState.Init)


            val dataState = awaitItem()
            assertTrue(dataState is MainUIState.Error)
        }
    }

}