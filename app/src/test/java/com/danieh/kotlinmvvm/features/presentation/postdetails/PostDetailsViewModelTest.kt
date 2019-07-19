package com.danieh.kotlinmvvm.features.presentation.postdetails

import arrow.core.Either
import com.danieh.kotlinmvvm.AndroidTest
import com.danieh.kotlinmvvm.features.domain.model.Comment
import com.danieh.kotlinmvvm.features.domain.usecase.GetCommentsUseCase
import com.nhaarman.mockito_kotlin.any
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.notification.Failure
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

/**
 * Created by danieh on 21/04/2019.
 */
class PostDetailsViewModelTest : AndroidTest() {

    @Mock
    lateinit var getCommentsUseCase: GetCommentsUseCase

    lateinit var postDetailsViewModel: PostDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        postDetailsViewModel = PostDetailsViewModel(getCommentsUseCase)
    }

    @Test
    fun `get posts, retrieve all posts from the use case successfully`() {

        // Given or Arrange
        val commentsList = listOf(Comment(0, 0, "Comment0", "email0", "body"), Comment(1, 1, "Comment1", "email1", "body"))
        `when`(getCommentsUseCase(any(), any())).thenAnswer { answer ->
            answer.getArgument<(Either<Failure, List<Comment>>) -> Unit>(1)(Either.Right(commentsList))
        }

        // Then or Assert
        postDetailsViewModel.commentList.observeForever {
            assertEquals(it!!.size, 2)
            assertEquals(it[0].postId, 0)
            assertEquals(it[0].name, "Comment0")
        }

        // When or Act
        runBlocking { postDetailsViewModel.getCommentsByPostId(0) }
    }
}
