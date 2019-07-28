package com.danieh.kotlinmvvm.features.domain.usecase

import arrow.core.Either
import com.danieh.kotlinmvvm.UnitTest
import com.danieh.kotlinmvvm.core.exception.Failure
import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.features.data.repository.PostsRepository
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.danieh.kotlinmvvm.features.domain.model.User
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by danieh on 23/04/2019.
 */
class GetPostsUsersUseCaseTest : UnitTest() {

    private lateinit var getPostsUsersUseCase: GetPostsUsersUseCase

    @Mock
    private lateinit var postsRepository: PostsRepository

    @Before
    fun setUp() {
        getPostsUsersUseCase = GetPostsUsersUseCase(postsRepository)
    }

    @Test
    fun `should get data from repository (posts and users) and join them in a list`() {
        val posts = listOf(
            Post(1, 1, "title1", "body1"),
            Post(2, 2, "title2", "body2"),
            Post(1, 3, "title3", "body3"),
            Post(2, 4, "title4", "body4")
        )
        val users = listOf(
            User(1, "Maria", "maria", "maria"),
            User(2, "Pepe", "pepe", "pepe")
        )
        val postsUsersResult = listOf(
            PostUserView(1, 1, "title1", "body1", "Maria"),
            PostUserView(2, 2, "title2", "body2", "Pepe"),
            PostUserView(1, 3, "title3", "body3", "Maria"),
            PostUserView(2, 4, "title4", "body4", "Pepe")
        )
        given { postsRepository.posts() }.willReturn(Either.Right(posts))
        given { postsRepository.users() }.willReturn(Either.Right(users))

        var result: Either<Failure, List<PostUserView>>? = null
        runBlocking {
            result = getPostsUsersUseCase.run(UseCase.None())
            result
        }

        verify(postsRepository).posts()
        verify(postsRepository).users()
        result shouldEqual Either.Right(postsUsersResult)
        verifyNoMoreInteractions(postsRepository)
    }
}