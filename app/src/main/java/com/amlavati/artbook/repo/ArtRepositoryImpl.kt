package com.amlavati.artbook.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.amlavati.artbook.api.Api
import com.amlavati.artbook.api.ImageResponse
import com.amlavati.artbook.room.Art
import com.amlavati.artbook.room.ArtDao
import com.amlavati.artbook.util.Resource
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val artDao: ArtDao,
    private val api: Api
) : ArtRepository {
    override suspend fun insertArt(art: Art) {
        Log.d("thread", "insertArt${Thread.currentThread().name}")
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = api.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error(response.errorBody()?.string()!!, null)
            }
        } catch (e: java.lang.Exception) {
            Resource.error("Error", null)
        }
    }

}