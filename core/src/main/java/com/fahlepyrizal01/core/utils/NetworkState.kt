package com.fahlepyrizal01.core.utils

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val message: String? = null
) {
    companion object {
        val FIRST_LOADING = NetworkState(Status.FIRST_LOADING)
        val LOADING = NetworkState(Status.LOADING)
        val SUCCESS = NetworkState(Status.SUCCESS)
        val FIRST_EMPTY_DATA = NetworkState(Status.FIRST_EMPTY_DATA)
        val EMPTY_DATA = NetworkState(Status.EMPTY_DATA)
        val FIRST_FAILED = NetworkState(Status.FIRST_FAILED)
        val FAILED = NetworkState(Status.FAILED)

        fun error(status: NetworkState, message: String?) = NetworkState(status.status, message)
    }
}