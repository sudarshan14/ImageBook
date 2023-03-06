package com.amlavati.artbook.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.amlavati.artbook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

const val TEST_DB_NAME = "testDatabase"

@SmallTest
@HiltAndroidTest
class ArtDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named(TEST_DB_NAME)
    lateinit var database: ArtDatabase

    //    private lateinit var database: ArtDatabase
    private lateinit var dao: ArtDao

    @Before
    fun setUp() {
        /*  val context = ApplicationProvider.getApplicationContext<Context>()
         database = Room.inMemoryDatabaseBuilder(context, ArtDatabase::class.java)
             .allowMainThreadQueries()
             .build()*/

        hiltRule.inject()
        dao = database.artDao()


    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun insertArtTesting() {

        runBlocking {
            val exampleArt = Art("Sunrise", "Sudarshan", 2022, "amlavati.com", 1)
            dao.insertArt(exampleArt)
            val list = dao.observeArts().getOrAwaitValue()

            assertThat(list).contains(exampleArt)
        }
    }

    @Test
    fun deleteArtTesting() {
        runBlocking {

            val exampleArt1 = Art("Sunrise1", "Sudarshan", 2022, "amlavati.com", 1)
            val exampleArt2 = Art("Sunrise2", "Sudarshan", 2022, "amlavati.com", 2)
            dao.insertArt(exampleArt1)
            dao.insertArt(exampleArt2)
            val listBeforeDelete = dao.observeArts().getOrAwaitValue()
            dao.deleteArt(exampleArt1)
            val listAfterDelete = dao.observeArts().getOrAwaitValue()
            assertThat(listAfterDelete.size).isLessThan(listBeforeDelete.size)

        }

    }
}