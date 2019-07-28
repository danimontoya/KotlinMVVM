package com.danieh.kotlinmvvm.features.presentation.posts

import arrow.core.Either
import com.danieh.kotlinmvvm.AndroidTest
import com.danieh.kotlinmvvm.features.domain.usecase.GetPostsUsersUseCase
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
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
class PostsViewModelTest : AndroidTest() {

    @Mock
    lateinit var getPostsUsersUseCase: GetPostsUsersUseCase

    lateinit var postsViewModel: PostsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        postsViewModel = PostsViewModel(getPostsUsersUseCase)
    }

    @Test
    fun `get posts and users, retrieve all PostsUsersView from the use case successfully`() {

        // Given or Arrange
        val postsUsersResult = listOf(
            PostUserView(1, 1, "title1", "body1", "Maria"),
            PostUserView(2, 2, "title2", "body2", "Pepe"),
            PostUserView(1, 3, "title3", "body3", "Maria"),
            PostUserView(2, 4, "title4", "body4", "Pepe")
        )
        `when`(getPostsUsersUseCase(any(), any())).thenAnswer { answer ->
            answer.getArgument<(Either<Failure, List<PostUserView>>) -> Unit>(1)(Either.Right(postsUsersResult))
        }

        // Then or Assert
        postsViewModel.postUserList.observeForever {
            assertEquals(it!!.size, 4)
            assertEquals(it[0].id, 1)
            assertEquals(it[0].title, "title1")
            assertEquals(it[1].id, 2)
            assertEquals(it[1].title, "title2")
            assertEquals(it[2].userName, "Maria")
        }

        // When or Act
        runBlocking { postsViewModel.getPostsUsers() }
    }
}
