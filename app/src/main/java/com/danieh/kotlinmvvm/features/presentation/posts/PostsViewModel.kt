package com.danieh.kotlinmvvm.features.presentation.posts

import androidx.lifecycle.MutableLiveData
import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.core.platform.BaseViewModel
import com.danieh.kotlinmvvm.features.domain.model.Post
import com.danieh.kotlinmvvm.features.domain.model.User
import com.danieh.kotlinmvvm.features.domain.usecase.GetPostsUseCase
import com.danieh.kotlinmvvm.features.domain.usecase.GetUsersUseCase
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
import com.danieh.kotlinmvvm.features.presentation.model.PostView
import com.danieh.kotlinmvvm.features.presentation.model.UserView
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    var postList: MutableLiveData<List<PostView>> = MutableLiveData()

    var userList: MutableLiveData<List<UserView>> = MutableLiveData()

    var postUserList: MutableLiveData<List<PostUserView>> = MutableLiveData()

    fun getPosts() = getPostsUseCase(UseCase.None()) {
        it.fold(::handleFailure, ::handlePosts)
    }

    fun getUsers() = getUsersUseCase(UseCase.None()) {
        it.fold(::handleFailure, ::handleUsers)
    }

    private fun handlePosts(posts: List<Post>) {
        val postListValue = posts.map { it.toPostView() }
        postList.value = postListValue

        if (!userList.value.isNullOrEmpty()) {
            createPostUserList(postListValue)
        }
    }

    private fun handleUsers(users: List<User>) {
        userList.value = users.map { it.toUserView() }

        val postListValue = postList.value
        if (!postListValue.isNullOrEmpty()) {
            createPostUserList(postListValue)
        }
    }

    private fun createPostUserList(postListValue: List<PostView>) {
        val listPostUserView = mutableListOf<PostUserView>()

        for (postView: PostView in postListValue) {
            val userView = userList.value?.find { postView.userId == it.id }
            val postUserView = PostUserView(
                postView.userId, postView.id, postView.title, postView.body, userView?.name
                    ?: "Unknown"
            )
            listPostUserView.add(postUserView)
        }

        postUserList.value = listPostUserView
    }
}
