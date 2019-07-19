package com.danieh.kotlinmvvm.features.presentation.postdetails

import androidx.lifecycle.MutableLiveData
import com.danieh.kotlinmvvm.core.platform.BaseViewModel
import com.danieh.kotlinmvvm.features.domain.model.Comment
import com.danieh.kotlinmvvm.features.domain.usecase.GetCommentsUseCase
import com.danieh.kotlinmvvm.features.presentation.model.CommentView
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
class PostDetailsViewModel @Inject constructor(private val getCommentsUseCase: GetCommentsUseCase) : BaseViewModel() {

    var commentList: MutableLiveData<List<CommentView>> = MutableLiveData()

    fun getCommentsByPostId(postId: Int) =
        getCommentsUseCase(GetCommentsUseCase.Params(postId)) {
            it.fold(::handleFailure, ::onGetCommentsSuccess)
        }

    private fun onGetCommentsSuccess(comments: List<Comment>) {
        commentList.value = comments.map { it.toCommentView() }
    }
}
