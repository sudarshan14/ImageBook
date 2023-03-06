package com.amlavati.artbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.amlavati.artbook.R
import com.amlavati.artbook.getOrAwaitValue
import com.amlavati.artbook.launchFragmentInHiltContainer
import com.amlavati.artbook.repo.FakeArtRepositoryImpl
import com.amlavati.artbook.room.Art
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

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    lateinit var navController: NavController

    @Before
    fun setUp() {
        hiltRule.inject()
        navController = Mockito.mock(NavController::class.java)


    }

    @Test
    fun testNavigateFromArtDetailsToImageApi() {
        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(click())
        Mockito.verify(navController).navigate(R.id.action_artDetailFragment_to_imageApiFragment)
    }

//    @Test
//    fun testOnBackPressed() {
//        Espresso.pressBack()
//        Mockito.verify(navController).popBackStack()
//    }

    @Test
    fun testSaveButtonClick() {
        val testViewModel = ArtViewModel(FakeArtRepositoryImpl())
        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            this as ArtDetailFragment
            viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.editTextArtName)).perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.editTextArtistName)).perform(replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.editTextYear)).perform(replaceText("1700"))
        Espresso.onView(withId(R.id.submit)).perform(click())

        val list = testViewModel.artList.getOrAwaitValue()
        assertThat(list).contains(
            Art(
                "Mona Lisa",
                "Da Vinci",
                1700, ""
            )
        )

    }


}