package com.danieh.kotlinmvvm.features.presentation.posts

import arrow.core.Either
import com.danieh.kotlinmvvm.AndroidTest
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.danieh.kotlinmvvm.features.domain.model.User
import com.danieh.kotlinmvvm.features.domain.usecase.GetPostsUseCase
import com.danieh.kotlinmvvm.features.domain.usecase.GetUsersUseCase
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
    lateinit var getPostsUseCase: GetPostsUseCase

    @Mock
    lateinit var getUsersUseCase: GetUsersUseCase

    lateinit var postsViewModel: PostsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        postsViewModel = PostsViewModel(getPostsUseCase, getUsersUseCase)
    }

    @Test
    fun `get posts, retrieve all posts from the use case successfully`() {

        // Given or Arrange
        val posts = listOf(Post(0, 0, "Post0", "body"), Post(1, 1, "Post1", "body"))
        `when`(getPostsUseCase(any(), any())).thenAnswer { answer ->
            answer.getArgument<(Either<Failure, List<Post>>) -> Unit>(1)(Either.Right(posts))
        }

        // Then or Assert
        postsViewModel.postList.observeForever {
            assertEquals(it!!.size, 2)
            assertEquals(it[0].id, 0)
            assertEquals(it[0].title, "Post0")
            assertEquals(it[1].id, 1)
            assertEquals(it[1].title, "Post1")
        }

        // When or Act
        runBlocking { postsViewModel.getPosts() }
    }

    @Test
    fun `get users, retrieve all users from the use case successfully`() {

        // Given or Arrange
        val users = listOf(User(0, "name0", "username0", "email"), User(1, "name1", "username1", "email"))
        `when`(getUsersUseCase(any(), any())).thenAnswer { answer ->
            answer.getArgument<(Either<Failure, List<User>>) -> Unit>(1)(Either.Right(users))
        }

        // Then or Assert
        postsViewModel.userList.observeForever {
            assertEquals(it!!.size, 2)
            assertEquals(it[0].id, 0)
            assertEquals(it[0].name, "name0")
            assertEquals(it[1].id, 1)
            assertEquals(it[1].name, "name1")
        }

        // When or Act
        runBlocking { postsViewModel.getUsers() }
    }
}
