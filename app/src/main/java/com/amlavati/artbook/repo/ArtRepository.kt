package com.amlavati.artbook.repo

import androidx.lifecycle.LiveData
import com.amlavati.artbook.api.ImageResponse
import com.amlavati.artbook.room.Art
import com.amlavati.artbook.util.Resource

interface ArtRepository {

    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArt(): LiveData<List<Art>>
    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}