package com.fahlepyrizal01.core.data.source.remote

import com.fahlepyrizal01.core.data.source.remote.network.ApiResponse
import com.fahlepyrizal01.core.data.source.remote.response.UsersResponse

interface IRemoteDataSource {
    suspend fun searchUsers(keyWord: String, page: Int, perPage: Int): ApiResponse<UsersResponse>
}