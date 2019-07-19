package com.danieh.kotlinmvvm.features.domain.usecase

import arrow.core.Either
import com.danieh.kotlinmvvm.UnitTest
import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.features.data.repository.PostsRepository
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by danieh on 23/04/2019.
 */
class GetPostsUseCaseTest : UnitTest() {

    private lateinit var getPostsUseCase: GetPostsUseCase

    @Mock
    private lateinit var postsRepository: PostsRepository

    @Before
    fun setUp() {
        getPostsUseCase = GetPostsUseCase(postsRepository)
        given { postsRepository.posts() }.willReturn(Either.Right(listOf(Post.empty())))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getPostsUseCase.run(UseCase.None()) }

        verify(postsRepository).posts()
        verifyNoMoreInteractions(postsRepository)
    }
}