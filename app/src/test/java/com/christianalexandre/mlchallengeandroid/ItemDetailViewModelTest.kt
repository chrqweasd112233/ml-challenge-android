package com.christianalexandre.mlchallengeandroid

import app.cash.turbine.test
import com.christianalexandre.domain.model.ApiException
import com.christianalexandre.domain.model.ApiResponse
import com.christianalexandre.mlchallengeandroid.modules.itemdetail.ItemDetailViewModel
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
class ItemDetailViewModelTest {

    private lateinit var itemRepository: com.christianalexandre.domain.repository.ItemRepository
    private lateinit var itemDetailViewModel: ItemDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        itemRepository = mockk()
        itemDetailViewModel = ItemDetailViewModel(itemRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region Item Detail
    @Test
    fun `fetch item detail state with success from api to test Detail State`() = runTest {
        val responseDetailMock = ApiResponse.Success(SearchMockManager.itemDetailMock)

        coEvery { itemRepository.getItemDetail("mock") } returns responseDetailMock

        itemDetailViewModel.fetchDetail("mock")

        itemDetailViewModel.itemsDetailState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch item detail state with success when data has nullable values to test Detail State`() =
        runTest {
            val responseDetailMock = ApiResponse.Success(SearchMockManager.itemDetailNullableMock)

            coEvery { itemRepository.getItemDetail("mock") } returns responseDetailMock

            itemDetailViewModel.fetchDetail("mock")

            itemDetailViewModel.itemsDetailState.test {
                assertTrue(awaitItem() is GenericUiState.Uninitialized)
                assertTrue(awaitItem() is GenericUiState.Loading)
                assertTrue(awaitItem() is GenericUiState.Success)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetch item detail with error api result`() = runTest {
        coEvery { itemRepository.getItemDetail("mock") } returns ApiResponse.Error(
            ApiException(500, "Exception mock")
        )

        itemDetailViewModel.fetchDetail("mock")

        itemDetailViewModel.itemsDetailState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
    // endregion

    // region Item Description
    @Test
    fun `fetch item description information with success api result to test Description State`() =
        runTest {
            val responseDescriptionMock = ApiResponse.Success(SearchMockManager.itemDescriptionMock)

            coEvery { itemRepository.getItemDescription("mock") } returns responseDescriptionMock

            itemDetailViewModel.fetchDescription("mock")

            itemDetailViewModel.itemDescriptionState.test {
                assertTrue(awaitItem() is GenericUiState.Uninitialized)
                assertTrue(awaitItem() is GenericUiState.Loading)
                assertTrue(awaitItem() is GenericUiState.Success)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetch item description information with success when data has nullable values to test Description State`() =
        runTest {
            val responseDescriptionMock =
                ApiResponse.Success(SearchMockManager.itemDescriptionNullableMock)

            coEvery { itemRepository.getItemDescription("mock") } returns responseDescriptionMock

            itemDetailViewModel.fetchDescription("mock")

            itemDetailViewModel.itemDescriptionState.test {
                assertTrue(awaitItem() is GenericUiState.Uninitialized)
                assertTrue(awaitItem() is GenericUiState.Loading)
                assertTrue(awaitItem() is GenericUiState.Error)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetch item description with error api result`() = runTest {
        coEvery { itemRepository.getItemDescription("mock") } returns ApiResponse.Error(
            ApiException(500, "Exception mock")
        )

        itemDetailViewModel.fetchDescription("mock")

        itemDetailViewModel.itemDescriptionState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
    // endregion

    // region Item Category
    @Test
    fun `fetch item category information with success api result to test Category State`() =
        runTest {
            val responseMock = ApiResponse.Success(SearchMockManager.itemCategorynMock)

            coEvery { itemRepository.getItemCategory("mock") } returns responseMock

            itemDetailViewModel.fetchCategory("mock")

            itemDetailViewModel.itemCategoryState.test {
                assertTrue(awaitItem() is GenericUiState.Uninitialized)
                assertTrue(awaitItem() is GenericUiState.Loading)
                assertTrue(awaitItem() is GenericUiState.Success)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetch item category information with success when data has nullable values to test Category State`() =
        runTest {
            val responseMock = ApiResponse.Success(SearchMockManager.itemCategoryMockNullableMock)

            coEvery { itemRepository.getItemCategory("mock") } returns responseMock

            itemDetailViewModel.fetchCategory("mock")

            itemDetailViewModel.itemCategoryState.test {
                assertTrue(awaitItem() is GenericUiState.Uninitialized)
                assertTrue(awaitItem() is GenericUiState.Loading)
                assertTrue(awaitItem() is GenericUiState.Success)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetch item category with error api result`() = runTest {
        coEvery { itemRepository.getItemCategory("mock") } returns ApiResponse.Error(
            ApiException(500, "Exception mock")
        )

        itemDetailViewModel.fetchCategory("mock")

        itemDetailViewModel.itemCategoryState.test {
            assertTrue(awaitItem() is GenericUiState.Uninitialized)
            assertTrue(awaitItem() is GenericUiState.Loading)
            assertTrue(awaitItem() is GenericUiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
    // endregion
}