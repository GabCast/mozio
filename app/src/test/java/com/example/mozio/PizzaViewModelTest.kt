package com.example.mozio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mozio.pizza.model.Menu
import com.example.mozio.pizza.repository.PizzaRepository
import com.example.mozio.pizza.repository.PizzaRepositoryImpl
import com.example.mozio.pizza.ui.PizzaViewmodel
import io.mockk.mockk
import io.mockk.MockKAnnotations
import io.mockk.spyk
import io.mockk.verify
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class PizzaViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PizzaViewmodel

    @RelaxedMockK
    private lateinit var repository: PizzaRepository

    private val observerError: Observer<Boolean> = mockk()
    private val observerSuccess: Observer<ArrayList<Menu>> = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init()
        val clientMock = HttpNetworkingMock()
        clientMock.mock()
        repository = spyk(PizzaRepositoryImpl(clientMock.client.getClient()))
        viewModel = PizzaViewmodel(repository).apply {
            onSuccess.observeForever(observerSuccess)
            onConnectionError.observeForever(observerError)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun amount_should_post_on_success() {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val responseBody = getMenu().toResponseBody(mediaType)

        runGenericTest(responseBody, 200, observerSuccess, ArrayList())

        verify(exactly = 1) { observerSuccess.onChanged(ArrayList()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun amount_should_post_on_error() {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val responseBody = getMenu().toResponseBody(mediaType)

        runGenericTest(responseBody, 400, observerError, true)

        verify(exactly = 1) { observerError.onChanged(true) }
    }

    @ExperimentalCoroutinesApi
    private fun <T> runGenericTest(
        responseBody: ResponseBody? = null,
        responseCode: Int,
        observer: Observer<T>,
        clazz: T
    ) {
        val response: Response<ArrayList<Menu>> = mockk()
        val result: ArrayList<Menu> = mockk()
        coroutineRule.testDispatcher.run {
            every { observer.onChanged(clazz) } answers {}
            every { response.code() } returns responseCode
            every { response.errorBody() } returns responseBody
            coEvery { repository.menu() } answers { result }
            viewModel.menu()
        }
    }

    private fun getMenu(): String {
        return "[\n" +
                "    {\n" +
                "        \"name\": \"Mozzarella\",\n" +
                "        \"price\": 10\n" +
                "    }, {\n" +
                "        \"name\": \"Pepperoni\",\n" +
                "        \"price\": 12\n" +
                "    }, {\n" +
                "        \"name\": \"Vegetarian\",\n" +
                "        \"price\": 9.5\n" +
                "    }, {\n" +
                "        \"name\": \"Super cheese\",\n" +
                "        \"price\": 11\n" +
                "    }\n" +
                "]"
    }
}
