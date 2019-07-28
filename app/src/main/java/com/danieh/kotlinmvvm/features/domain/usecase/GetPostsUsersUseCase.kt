package com.danieh.kotlinmvvm.features.domain.usecase

import arrow.core.Either
import arrow.core.getOrElse
import com.danieh.kotlinmvvm.core.exception.Failure
import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.features.data.repository.PostsRepository
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.danieh.kotlinmvvm.features.domain.model.User
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
import com.danieh.kotlinmvvm.features.presentation.model.PostView
import javax.inject.Inject

/**
 * Created by danieh on 28/07/2019.
 */
class GetPostsUsersUseCase @Inject constructor(private val postsRepository: PostsRepository) :
    UseCase<List<PostUserView>, UseCase.None>() {

    override suspend fun run(params: None) = zip(postsRepository.posts(), postsRepository.users())

    private fun zip(
        posts: Either<Failure, List<Post>>,
        users: Either<Failure, List<User>>
    ): Either<Failure, List<PostUserView>> {
        return if (posts.isRight() && users.isRight()) {

            val listPostUserView = mutableListOf<PostUserView>()

            val postsView = posts.getOrElse { emptyList() }.map { it.toPostView() }
            val usersView = users.getOrElse { emptyList() }.map { it.toUserView() }

            for (postView: PostView in postsView) {
                val userView = usersView.find { postView.userId == it.id }
                val postUserView = PostUserView(
                    postView.userId,
                    postView.id,
                    postView.title,
                    postView.body,
                    userView?.name ?: "Unknown"
                )
                listPostUserView.add(postUserView)
            }

            Either.right(listPostUserView)
        } else {
            Either.left(Failure.PostsUsersError())
        }
    }
}
