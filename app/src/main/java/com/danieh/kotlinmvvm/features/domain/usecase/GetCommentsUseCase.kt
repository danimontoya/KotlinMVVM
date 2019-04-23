package com.danieh.kotlinmvvm.features.domain.usecase

import com.danieh.kotlinmvvm.core.functional.map
import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.features.data.repository.PostsRepository
import com.danieh.kotlinmvvm.features.domain.model.Comment
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
class GetCommentsUseCase @Inject constructor(private val postsRepository: PostsRepository) :
        UseCase<List<Comment>, GetCommentsUseCase.Params>() {

    override suspend fun run(params: Params) = postsRepository.comments()
            .map { commentList -> commentList.filter { params.postId == it.postId } }

    data class Params(val postId: Int)
}
