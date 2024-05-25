package com.viridis

import com.viridis.data.models.NewsModel
import com.viridis.data.repositories.NewsRepository
import com.viridis.data.repositories.NewsRepositoryImpl
import com.viridis.service.ApiService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var repository: NewsRepository

    @MockK
    lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = NewsRepositoryImpl(apiService)
    }

    @Test
    fun exampleTest() = runTest {
        // GIVEN
        every { repository.getNews() } returns flow { listOf(NewsModel()) }

        // WHEN
        val testObserver = repository.getNews().count()

        assertEquals(1, testObserver)
    }
}