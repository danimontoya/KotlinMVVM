package com.danieh.kotlinmvvm.features.presentation.posts

import androidx.lifecycle.MutableLiveData
import com.danieh.kotlinmvvm.core.interactor.UseCase
import com.danieh.kotlinmvvm.core.platform.BaseViewModel
import com.danieh.kotlinmvvm.features.domain.usecase.GetPostsUsersUseCase
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
class PostsViewModel @Inject constructor(private val getPostsUsersUseCase: GetPostsUsersUseCase) : BaseViewModel() {

    var postUserList: MutableLiveData<List<PostUserView>> = MutableLiveData()

    fun getPostsUsers() = getPostsUsersUseCase(UseCase.None()) {
        it.fold(::handleFailure, ::handlePostsUsersView)
    }

    private fun handlePostsUsersView(posts: List<PostUserView>) {
        postUserList.value = posts
    }
}
