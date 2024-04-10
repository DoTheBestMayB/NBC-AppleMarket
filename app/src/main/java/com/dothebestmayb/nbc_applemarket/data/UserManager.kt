package com.dothebestmayb.nbc_applemarket.data

import com.dothebestmayb.nbc_applemarket.model.User

object UserManager {
    private val users = hashMapOf<String, User>() // Key: nickname

    fun addUser(newUsers: List<User>) {
        for (user in newUsers) {
            users[user.nickname] = user
        }
    }

    fun addUser(newUser: User) {
        users[newUser.nickname] = newUser
    }

    fun getUser(nickName: String): User? {
        return users[nickName]
    }

}