package com.fahlepyrizal01.core.data.source.remote.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.fahlepyrizal01.core.data.Resource
import com.fahlepyrizal01.core.data.source.remote.IRemoteDataSource
import com.fahlepyrizal01.core.data.source.remote.network.ApiResponse
import com.fahlepyrizal01.core.data.source.remote.response.UsersResponse
import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.core.utils.DataMapper
import com.fahlepyrizal01.core.utils.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageDataSource @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val keyword: String
) : ItemKeyedDataSource<Int, User>() {

    private var incompleteResults = false
    private var page = DEFAULT_PAGE_NUMBER
    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    val searchUserNetworkState = MutableLiveData<NetworkState>()
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<User>) {
        searchUserNetworkState.postValue(NetworkState.FIRST_LOADING)
        fetchData(true) { callback.onResult(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<User>) {
        if (!incompleteResults) {
            searchUserNetworkState.postValue(NetworkState.LOADING)
            fetchData(false) { callback.onResult(it) }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<User>) {
        // Do Nothing
    }

    private fun fetchData(isInitial: Boolean, callback: (List<User>) -> Unit) {
        coroutineScope.launch {
            when (val apiResponse =
                remoteDataSource.searchUsers(keyword, page, DEFAULT_PER_PAGE_NUMBER)) {
                is ApiResponse.Success -> {
                    onSuccessHandler(
                        isInitial,
                        Resource.Success(apiResponse.data),
                        callback
                    )
                }
                is ApiResponse.Empty -> {
                    onSuccessHandler(
                        isInitial,
                        Resource.Success(
                            UsersResponse(DEFAULT_TOTAL_COUNT, true, listOf())
                        ),
                        callback
                    )
                }
                is ApiResponse.Error -> {
                    onErrorHandler(isInitial, Resource.Error(apiResponse.errorMessage))
                }
            }
        }
    }

    private fun onSuccessHandler(
        isInitial: Boolean,
        response: Resource<UsersResponse>,
        callback: (List<User>) -> Unit
    ) {
        if (response.data?.items.isNullOrEmpty()) {
            searchUserNetworkState.postValue(
                if (isInitial) NetworkState.FIRST_EMPTY_DATA else NetworkState.EMPTY_DATA
            )
        } else {
            response.data.let {
                incompleteResults = it?.incompleteResults ?: false
                page += DEFAULT_PAGE_NUMBER
                callback(DataMapper.mapResponseToDomain(it?.items ?: listOf()))
                searchUserNetworkState.postValue(NetworkState.SUCCESS)
            }
        }
    }

    private fun onErrorHandler(isInitial: Boolean, response: Resource<List<User>>) {
        searchUserNetworkState.postValue(
            NetworkState.error(
                if (isInitial) NetworkState.FIRST_FAILED else NetworkState.FAILED,
                response.message
            )
        )
    }

    override fun getKey(item: User): Int = page

    fun clearCoroutineJobs() = completableJob.cancel()

    companion object {
        private const val DEFAULT_PAGE_NUMBER = 1
        private const val DEFAULT_PER_PAGE_NUMBER = 10
        private const val DEFAULT_TOTAL_COUNT = 0
    }
}