package com.danieh.kotlinmvvm.features.presentation.postdetails

import com.danieh.kotlinmvvm.R
import com.danieh.kotlinmvvm.features.presentation.model.CommentView
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.layout_row_comment.view.*

/**
 * Created by danieh on 25/07/2019.
 */
class CommentItem(private val commentView: CommentView) :
        Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            itemView.comment_name.text = commentView.name
            itemView.comment_body.text = commentView.body
            itemView.comment_email.text = commentView.email
        }
    }

    override fun getLayout(): Int = R.layout.layout_row_comment
}