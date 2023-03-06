package com.amlavati.artbook.di

import android.content.Context
import androidx.room.Room
import com.amlavati.artbook.R
import com.amlavati.artbook.api.Api
import com.amlavati.artbook.repo.ArtRepositoryImpl
import com.amlavati.artbook.repo.ArtRepository
import com.amlavati.artbook.room.ArtDatabase
import com.amlavati.artbook.util.Constants.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtDatabase"
    ).build()

    @Provides
    @Singleton
    fun injectDao(database: ArtDatabase) = database.artDao()


    @Provides
    @Singleton
    fun injectRetrofitApi(): Api {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        db: ArtDatabase,
        api: Api
    ): ArtRepository {
        return ArtRepositoryImpl(db.artDao(), api)
    }

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

}