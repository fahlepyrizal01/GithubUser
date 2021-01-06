package com.fahlepyrizal01.core.domain.usecase

import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.core.domain.repository.IRepository
import com.fahlepyrizal01.core.utils.Listing
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: IRepository) : IUseCase {
    override fun searchUsers(keyWord: String): Listing<User> =
        repository.searchUsers(keyWord)
}