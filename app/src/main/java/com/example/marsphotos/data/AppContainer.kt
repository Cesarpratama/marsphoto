
package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * Sebagai wadah dependensi di aplikasi
 */
interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

/**
 * implementasi untuk dependensi di aplikasi
 *
 * variable diinisiasikan secara lazy dan dibagi di seluruh aplikasi
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    /**
     * menggunakan retrofit untuk membuat objek retrofit
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * object retrofit untuk membuat panggilan API
     */
    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    /**
     * Implementasi untuk repository foto mars
     */
    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}
