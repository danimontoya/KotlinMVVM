package com.danieh.kotlinmvvm.features.data.model

import com.danieh.kotlinmvvm.features.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostEntity(
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("body")
        val body: String
) {
    companion object {
        fun empty() = PostEntity(0, 0, "", "")
    }

    fun toPost() = Post(userId, id, title, body)
}
