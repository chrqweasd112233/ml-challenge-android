package com.christianalexandre.mlchallengeandroid

import app.cash.turbine.test
import com.christianalexandre.mlchallengeandroid.data.repository.SearchRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.modules.home.HomeUiState
import com.christianalexandre.mlchallengeandroid.modules.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var searchRepository: SearchRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        searchRepository = mockk()
        homeViewModel = HomeViewModel(searchRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch items with success api result`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.searchItemMock)

        coEvery { searchRepository.search("mock") } returns responseMock

        homeViewModel.fetchItems("mock")

        homeViewModel.eventsState.test {
            assertTrue(awaitItem() is HomeUiState.Uninitialized)
            assertTrue(awaitItem() is HomeUiState.Loading)
            assertTrue(awaitItem() is HomeUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch items success with empty list api result`() = runTest {
        coEvery { searchRepository.search("mock") } returns ApiResponse.Success(emptyList())

        homeViewModel.fetchItems("mock")

        homeViewModel.eventsState.test {
            assertTrue(awaitItem() is HomeUiState.Uninitialized)
            assertTrue(awaitItem() is HomeUiState.Loading)
            assertTrue(awaitItem() is HomeUiState.Empty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch items with error api result`() = runTest {
        coEvery { searchRepository.search("mock") } returns ApiResponse.Error(
            ApiException(500, "Exception mock")
        )

        homeViewModel.fetchItems("mock")

        homeViewModel.eventsState.test {
            assertTrue(awaitItem() is HomeUiState.Uninitialized)
            assertTrue(awaitItem() is HomeUiState.Loading)
            assertTrue(awaitItem() is HomeUiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}