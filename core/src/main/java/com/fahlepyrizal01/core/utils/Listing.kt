package com.fahlepyrizal01.core.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit,
    val clearCoroutineJobs: () -> Unit
)



