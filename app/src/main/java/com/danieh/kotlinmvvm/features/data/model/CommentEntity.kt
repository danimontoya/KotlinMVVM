package com.danieh.kotlinmvvm.features.data.model

import com.danieh.kotlinmvvm.features.domain.model.Comment

data class CommentEntity(
        val postId: Int,
        val id: Int,
        val name: String,
        val email: String,
        val body: String
) {
    companion object {
        fun empty() = CommentEntity(0, 0, "", "", "")
    }

    fun toComment() = Comment(postId, id, name, email, body)
}
