package com.danieh.kotlinmvvm.features.domain.model

import com.danieh.kotlinmvvm.features.presentation.model.UserView

data class User(
        val id: Int,
        val name: String,
        val username: String,
        val email: String
) {
    fun toUserView() = UserView(id, name, username, email)

    companion object {
        fun empty() = User(0, "", "", "")
    }
}
