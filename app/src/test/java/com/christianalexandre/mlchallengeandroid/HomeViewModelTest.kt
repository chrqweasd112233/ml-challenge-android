package com.christianalexandre.mlchallengeandroid

import app.cash.turbine.test
import com.christianalexandre.mlchallengeandroid.domain.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.modules.home.HomeViewModel
import com.christianalexandre.mlchallengeandroid.modules.util.ui.generic.GenericUiState
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

    private lateinit var itemRepository: ItemRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        itemRepository = mockk()
        homeViewModel = HomeViewModel(itemRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch items with success api result`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.searchItemMock)

        coEvery { itemRepository.search("mock") } returns responseMock

        homeViewModel.fetchItems("mock")

        homeViewModel.searchItemsState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch items with success api result when data has nullable values`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.searchItemNullableMock)

        coEvery { itemRepository.search("mock") } returns responseMock

        homeViewModel.fetchItems("mock")

        homeViewModel.searchItemsState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch items success with empty list api result`() = runTest {
        coEvery { itemRepository.search("mock") } returns ApiResponse.Success(emptyList())

        homeViewModel.fetchItems("mock")

        homeViewModel.searchItemsState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Empty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch items with error api result`() = runTest {
        coEvery { itemRepository.search("mock") } returns ApiResponse.Error(
            ApiException(500, "Exception mock")
        )

        homeViewModel.fetchItems("mock")

        homeViewModel.searchItemsState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}