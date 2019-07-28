package com.danieh.kotlinmvvm.features.presentation.posts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieh.kotlinmvvm.R
import com.danieh.kotlinmvvm.core.exception.Failure
import com.danieh.kotlinmvvm.core.extension.*
import com.danieh.kotlinmvvm.core.platform.BaseFragment
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
import com.danieh.kotlinmvvm.features.presentation.widget.empty.EmptyListener
import com.danieh.kotlinmvvm.features.presentation.widget.error.ErrorListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : BaseFragment() {

    override fun layoutId() = R.layout.fragment_posts

    private lateinit var viewModel: PostsViewModel

    private val postsAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            observe(postUserList, ::onGetPostsSuccess)
            failure(failure, ::showError)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()

        recycler_posts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
        }

        getPosts()
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            view_empty.gone()
            getPosts()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            view_error.gone()
            getPosts()
        }
    }

    private fun getPosts() {
        progress_posts.visible()
        viewModel.getPostsUsers()
    }

    private fun onGetPostsSuccess(posts: List<PostUserView>?) {
        view_error.gone()
        progress_posts.gone()
        if (posts != null && posts.isNotEmpty()) {
            val items = posts.map { postUserView ->
                PostItem(postUserView, clickListener = { postView ->
                    val navDirections = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment().apply {
                        postId = postView.id
                        postTitle = postView.title
                        postBody = postView.body
                        postAuthor = postView.userName
                    }
                    findNavController().navigate(navDirections)
                })
            }
            postsAdapter.addAll(items)
            recycler_posts.visible()
        } else {
            view_empty.visible()
        }
    }

    private fun showError(failure: Failure?) {
        progress_posts.gone()
        view_error.visible()
        when (failure) {
            is Failure.PostsUsersError -> notify("PostsUsersError")
            is Failure.ServerError -> notify("ServerError")
        }
    }
}
