package com.example.tmdb.di


import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb.R
import com.example.tmdb.remote.MoviesApi
import com.example.tmdb.remote.MoviesApi.Companion.BASE_URL
import com.example.tmdb.data.MovieDetailsDataSource
import com.example.tmdb.data.MoviesRepository
import com.example.tmdb.data.MoviesRepositoryImpl
import com.example.tmdb.data.PopularMoviesPagingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Shaheer cs on 22/03/2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun loggingInterceptorProvider(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun interceptorProvider(): Interceptor = Interceptor {
        val originalRequest = it.request()
        val builder =
            originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
        val newRequest = builder.build()
        it.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun converterFactoryProvider(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun okHttpClientProvider(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        addInterceptor(httpLoggingInterceptor)
    }.build()

    @Provides
    @Singleton
    fun retrofitProvider(
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(converterFactory)
        client(okHttpClient)
    }.build()

    @Provides
    @Singleton
    fun appServiceProvider(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)


    @Provides
    @Singleton
    fun initGlide(@ApplicationContext appContext: Context): RequestManager = Glide.with(appContext)
        .setDefaultRequestOptions(
            RequestOptions()
                .centerInside()
                .error(R.drawable.ic_movies)
        )

    @Provides
    @Singleton
    fun movieRepositoryProvider(
        movieDetailsDataSource: MovieDetailsDataSource,
        popularMoviesPagingDataSource: PopularMoviesPagingDataSource
    ): MoviesRepository =
        MoviesRepositoryImpl(movieDetailsDataSource, popularMoviesPagingDataSource)

    @Provides
    @Singleton
    fun popularMoviesPagingDataSourceProvider(moviesApi: MoviesApi) =
        PopularMoviesPagingDataSource(moviesApi)

    @Provides
    @Singleton
    fun movieDetailsDataSourceProvider(moviesApi: MoviesApi) = MovieDetailsDataSource(moviesApi)
}