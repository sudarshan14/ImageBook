package com.amlavati.artbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.amlavati.artbook.R
import com.amlavati.artbook.adapter.ImageRecyclerAdapter
import com.amlavati.artbook.getOrAwaitValue
import com.amlavati.artbook.launchFragmentInHiltContainer
import com.amlavati.artbook.repo.FakeArtRepositoryImpl
import com.amlavati.artbook.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testImageClick() {
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = ArtViewModel(FakeArtRepositoryImpl())
        val selectedImage = "www.amlavati.com"
        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)

            this as ImageApiFragment
            viewModel = testViewModel
            imageRecyclerAdapter.images = listOf(selectedImage)

        }

        Espresso.onView(withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,
                click()
            )
        )
        Mockito.verify(navController).navigate(R.id.action_imageApiFragment_to_artDetailFragment)
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImage)
    }

}