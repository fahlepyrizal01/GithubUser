package com.fahlepyrizal01.core.utils

import com.fahlepyrizal01.core.data.source.remote.response.UserResponse
import com.fahlepyrizal01.core.domain.model.User

object DataMapper {
    fun mapResponseToDomain(input: List<UserResponse>): List<User> {
        return input.map {
            User(
                login = it.login,
                id = it.id,
                nodeId = it.nodeId,
                avatarUrl = it.avatarUrl,
                gravatarId = it.gravatarId,
                url = it.url,
                htmlUrl = it.htmlUrl,
                followersUrl = it.followersUrl,
                followingUrl = it.followingUrl,
                gistsUrl = it.gistsUrl,
                starredUrl = it.starredUrl,
                subscriptionsUrl = it.subscriptionsUrl,
                organizationsUrl = it.organizationsUrl,
                reposUrl = it.reposUrl,
                eventsUrl = it.eventsUrl,
                receivedEventsUrl = it.receivedEventsUrl,
                type = it.type,
                siteAdmin = it.siteAdmin,
                score = it.score
            )
        }
    }
}