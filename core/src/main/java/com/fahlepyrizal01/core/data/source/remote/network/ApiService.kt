package com.fahlepyrizal01.core.data.source.remote.network

import com.fahlepyrizal01.core.data.source.remote.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<UsersResponse>
}