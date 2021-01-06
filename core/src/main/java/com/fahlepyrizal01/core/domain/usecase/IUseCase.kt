package com.fahlepyrizal01.core.domain.usecase

import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.core.utils.Listing

interface IUseCase {
    fun searchUsers(keyWord: String): Listing<User>
}