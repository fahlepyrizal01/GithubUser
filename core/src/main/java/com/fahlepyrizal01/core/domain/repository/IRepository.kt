package com.fahlepyrizal01.core.domain.repository

import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.core.utils.Listing

interface IRepository {
    fun searchUsers(keyWord: String): Listing<User>
}