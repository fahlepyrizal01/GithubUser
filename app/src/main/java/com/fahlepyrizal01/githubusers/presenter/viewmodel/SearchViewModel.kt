package com.fahlepyrizal01.githubusers.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.core.domain.usecase.IUseCase
import com.fahlepyrizal01.core.utils.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val useCase: IUseCase) : ViewModel() {

    var searchUserNetworkState: LiveData<NetworkState>? = null
    var searchUserPagedList: LiveData<PagedList<User>>? = null

    @ExperimentalCoroutinesApi
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    @ExperimentalCoroutinesApi
    @FlowPreview
    val search = queryChannel.asFlow()
        .debounce(TIME_OUT)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest { it }
        .asLiveData()

    fun searchUser(keyword: String) {
        useCase.searchUsers(keyword).also {
            searchUserPagedList = it.pagedList
            searchUserNetworkState = it.networkState
        }
    }

    companion object {
        const val TIME_OUT = 300L
    }

}