package com.danieh.kotlinmvvm.features.domain.usecase

import com.danieh.kotlinmvvm.UnitTest
import com.danieh.kotlinmvvm.core.exception.Failure
import com.danieh.kotlinmvvm.core.functional.Either
import com.danieh.kotlinmvvm.features.data.repository.PostsRepository
import com.danieh.kotlinmvvm.features.domain.model.Comment
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
class GetCommentsUseCaseTest : UnitTest() {

    private lateinit var getCommentsUseCase: GetCommentsUseCase

    @Mock
    private lateinit var postsRepository: PostsRepository

    @Before
    fun setUp() {
        getCommentsUseCase = GetCommentsUseCase(postsRepository)
    }

    @Test
    fun `should get data from repository`() {
        given { postsRepository.comments() }.willReturn(Either.Right(listOf(Comment.empty())))

        runBlocking { getCommentsUseCase.run(GetCommentsUseCase.Params(0)) }

        verify(postsRepository).comments()
        verifyNoMoreInteractions(postsRepository)
    }

    @Test
    fun `should get data from repository and filter it by post id`() {
        val postId = 2
        val comments = listOf(
            Comment(3, 0, "name0", "email0", "body0"),
            Comment(2, 1, "name1", "email1", "body1")
        )
        val commentsResult = listOf(Comment(2, 1, "name1", "email1", "body1"))
        given { postsRepository.comments() }.willReturn(Either.Right(comments))

        var result: Either<Failure, List<Comment>>? = null
        runBlocking {
            result = getCommentsUseCase.run(GetCommentsUseCase.Params(postId))
            result
        }

        verify(postsRepository).comments()
        result shouldEqual Either.Right(commentsResult)
        verifyNoMoreInteractions(postsRepository)
    }
}