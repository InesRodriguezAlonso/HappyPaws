package com.happypaws.res

import com.happypaws.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.*
import javax.inject.Singleton

@Module
class RestClientModule(private val cacheFile: File) {

    @Provides
    @Singleton
    internal fun provideRestClient(): Retrofit {
        var cache: Cache? = null
        try {
            cache = Cache(cacheFile, (10 * 1024 * 1024).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Log request and response body
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    // Customize the request
                    val request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .header("Cache-Control", String.format(Locale.getDefault(), "max-age=%d", BuildConfig.CACHETIME))
                            .build()


                    val response = chain.proceed(request)
                    response.cacheResponse()

                    response
                }
                .addInterceptor(logging)
                .cache(cache)
                .build()


        // Build the retrofit config from our http client
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesRestApiService(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesService(apiService: RestApiService): RestApi {
        return RestApi(apiService)
    }
}