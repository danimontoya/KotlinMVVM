package com.danieh.kotlinmvvm.features.domain.usecase

import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.features.data.repository.PostsRepository
import com.danieh.kotlinmvvm.features.domain.model.User
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
class GetUsersUseCase @Inject constructor(private val postsRepository: PostsRepository) :
        UseCase<List<User>, UseCase.None>() {

    override suspend fun run(params: None) = postsRepository.users()
}
