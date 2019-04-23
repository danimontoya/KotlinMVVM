package com.danieh.kotlinmvvm.features.data.repository

import com.danieh.kotlinmvvm.UnitTest
import com.danieh.kotlinmvvm.core.exception.Failure.NetworkConnection
import com.danieh.kotlinmvvm.core.exception.Failure.ServerError
import com.danieh.kotlinmvvm.core.functional.Either
import com.danieh.kotlinmvvm.core.functional.Either.Right
import com.danieh.kotlinmvvm.core.platform.NetworkHandler
import com.danieh.kotlinmvvm.features.data.datasource.PostsService
import com.danieh.kotlinmvvm.features.data.model.CommentEntity
import com.danieh.kotlinmvvm.features.data.model.PostEntity
import com.danieh.kotlinmvvm.features.domain.model.Comment
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

/**
 * Created by danieh on 23/04/2019.
 */
class PostsRepositoryTest : UnitTest() {

    private lateinit var repository: PostsRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: PostsService

    @Mock
    private lateinit var postsCall: Call<List<PostEntity>>
    @Mock
    private lateinit var postsResponse: Response<List<PostEntity>>

    @Mock
    private lateinit var commentsCall: Call<List<CommentEntity>>
    @Mock
    private lateinit var commentsResponse: Response<List<CommentEntity>>

    @Before
    fun setUp() {
        repository = PostsRepository.Network(networkHandler, service)
    }

    @Test
    fun `posts should return empty list by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { postsResponse.body() }.willReturn(null)
        given { postsResponse.isSuccessful }.willReturn(true)
        given { postsCall.execute() }.willReturn(postsResponse)
        given { service.posts() }.willReturn(postsCall)

        val posts = repository.posts()

        posts shouldEqual Right(emptyList<Post>())
        verify(service).posts()
    }

    @Test
    fun `should get post list from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { postsResponse.body() }.willReturn(listOf(PostEntity(0, 0, "title", "body")))
        given { postsResponse.isSuccessful }.willReturn(true)
        given { postsCall.execute() }.willReturn(postsResponse)
        given { service.posts() }.willReturn(postsCall)

        val posts = repository.posts()

        posts shouldEqual Right(listOf(Post(0, 0, "title", "body")))
        verify(service).posts()
    }

    @Test
    fun `posts service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val posts = repository.posts()

        posts shouldBeInstanceOf Either::class.java
        posts.isLeft shouldEqual true
        posts.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `posts service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val posts = repository.posts()

        posts shouldBeInstanceOf Either::class.java
        posts.isLeft shouldEqual true
        posts.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `posts service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val posts = repository.posts()

        posts shouldBeInstanceOf Either::class.java
        posts.isLeft shouldEqual true
        posts.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `posts request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val posts = repository.posts()

        posts shouldBeInstanceOf Either::class.java
        posts.isLeft shouldEqual true
        posts.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `comments should return empty list by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { commentsResponse.body() }.willReturn(null)
        given { commentsResponse.isSuccessful }.willReturn(true)
        given { commentsCall.execute() }.willReturn(commentsResponse)
        given { service.comments() }.willReturn(commentsCall)

        val comments = repository.comments()

        comments shouldEqual Right(emptyList<Comment>())
        verify(service).comments()
    }

    @Test
    fun `should get comment list from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { commentsResponse.body() }.willReturn(listOf(CommentEntity(0, 0, "", "", "")))
        given { commentsResponse.isSuccessful }.willReturn(true)
        given { commentsCall.execute() }.willReturn(commentsResponse)
        given { service.comments() }.willReturn(commentsCall)

        val comments = repository.comments()

        comments shouldEqual Right(listOf(Comment(0, 0, "", "", "")))
        verify(service).comments()
    }

    @Test
    fun `comments service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val comments = repository.comments()

        comments shouldBeInstanceOf Either::class.java
        comments.isLeft shouldEqual true
        comments.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `comments service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val comments = repository.comments()

        comments shouldBeInstanceOf Either::class.java
        comments.isLeft shouldEqual true
        comments.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `comments service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val comments = repository.comments()

        comments shouldBeInstanceOf Either::class.java
        comments.isLeft shouldEqual true
        comments.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test
    fun `comments request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val comments = repository.comments()

        comments shouldBeInstanceOf Either::class.java
        comments.isLeft shouldEqual true
        comments.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}