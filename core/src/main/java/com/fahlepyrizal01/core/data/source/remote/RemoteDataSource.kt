package com.fahlepyrizal01.core.data.source.remote

import com.fahlepyrizal01.core.data.source.remote.network.ApiResponse
import com.fahlepyrizal01.core.data.source.remote.network.ApiService
import com.fahlepyrizal01.core.data.source.remote.response.UsersResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {
    override suspend fun searchUsers(
        keyWord: String,
        page: Int,
        perPage: Int
    ): ApiResponse<UsersResponse> {
        return try {
            val response = apiService.searchUsers(keyWord, page, perPage)

            if (response.isSuccessful) response.body()?.run {
                ApiResponse.Success(this)
            } ?: ApiResponse.Error(response.message().toString())
            else {
                ApiResponse.Error(response.message().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse.Error(e.toString())
        }
    }
}