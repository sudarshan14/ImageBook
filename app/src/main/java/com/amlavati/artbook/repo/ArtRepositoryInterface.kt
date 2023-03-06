package com.amlavati.artbook.repo

import androidx.lifecycle.LiveData
import com.amlavati.artbook.room.Art

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art:Art)
    fun getArt():LiveData<List<Art>>
    suspend fun searchImage(imageString:String) :com.amlavati.artbook.api.Result
}