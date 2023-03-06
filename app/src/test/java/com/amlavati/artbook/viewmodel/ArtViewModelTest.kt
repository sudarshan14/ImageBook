package com.amlavati.artbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amlavati.artbook.MainCoroutineRule
import com.amlavati.artbook.getOrAwaitValueTest
import com.amlavati.artbook.repo.FakeArtRepositoryImpl
import com.amlavati.artbook.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setUp() {
        viewModel = ArtViewModel(FakeArtRepositoryImpl())
    }


    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Monalissa", "Vinchi", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

}