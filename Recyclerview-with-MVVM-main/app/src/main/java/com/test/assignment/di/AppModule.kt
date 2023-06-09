package com.test.assignment.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.assignment.data.remote.ProductRemoteDataSource
import com.test.assignment.data.remote.ProductService
import com.test.assignment.data.remote.NullOnEmptyConverterFactory
import com.test.assignment.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/")
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: ProductService) = ProductRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ProductRemoteDataSource) =
        ProductRepository(remoteDataSource)
}
