package com.danieh.kotlinmvvm.features.presentation.posts

import com.danieh.kotlinmvvm.R
import com.danieh.kotlinmvvm.features.presentation.model.PostUserView
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.layout_row_post.view.*

/**
 * Created by danieh on 25/07/2019.
 */
class PostItem(
        private val postUserView: PostUserView,
        private val clickListener: (PostUserView) -> Unit = { _ -> }
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            itemView.setOnClickListener { clickListener(postUserView) }
            itemView.post_title.text = postUserView.title
            itemView.post_author.text = postUserView.userName
        }
    }

    override fun getLayout(): Int = R.layout.layout_row_post
}