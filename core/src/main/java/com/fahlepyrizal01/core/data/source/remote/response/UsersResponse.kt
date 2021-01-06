package com.fahlepyrizal01.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class UsersResponse(
    @field:SerializedName("total_count") val totalCount: Int,
    @field:SerializedName("incomplete_results") val incompleteResults: Boolean,
    @field:SerializedName("items") val items: List<UserResponse>
)