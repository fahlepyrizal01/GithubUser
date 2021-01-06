package com.fahlepyrizal01.core.data

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.fahlepyrizal01.core.data.source.remote.RemoteDataSource
import com.fahlepyrizal01.core.data.source.remote.paging.DataSourceFactory
import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.core.domain.repository.IRepository
import com.fahlepyrizal01.core.utils.Listing
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : IRepository {
    override fun searchUsers(keyWord: String): Listing<User> {

        val dataSourceFactory = DataSourceFactory(remoteDataSource, keyWord)
        val pagedList = LivePagedListBuilder(
            dataSourceFactory, DataSourceFactory.pagedListConfig()
        ).build()

        return Listing(
            pagedList = pagedList,
            networkState = Transformations.switchMap(dataSourceFactory.searchUserLiveData)
            { it.searchUserNetworkState },
            retry = { dataSourceFactory.searchUserLiveData.value?.retryAllFailed() },
            refresh = { dataSourceFactory.searchUserLiveData.value?.invalidate() },
            clearCoroutineJobs = { dataSourceFactory.searchUserLiveData.value?.clearCoroutineJobs() }
        )
    }
}