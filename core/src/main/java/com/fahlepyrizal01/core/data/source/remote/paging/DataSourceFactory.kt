package com.fahlepyrizal01.core.data.source.remote.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.fahlepyrizal01.core.data.source.remote.IRemoteDataSource
import com.fahlepyrizal01.core.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceFactory @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val keyword: String,
) : DataSource.Factory<Int, User>() {

    val searchUserLiveData = MutableLiveData<PageDataSource>()

    override fun create(): DataSource<Int, User> {
        val source = PageDataSource(remoteDataSource, keyword)
        searchUserLiveData.postValue(source)
        return source
    }

    companion object {
        private const val PREFETCH_DISTANCE = 1

        fun pagedListConfig() = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()
    }
}