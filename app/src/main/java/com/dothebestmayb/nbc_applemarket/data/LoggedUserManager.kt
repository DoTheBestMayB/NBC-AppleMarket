package com.dothebestmayb.nbc_applemarket.data

import com.dothebestmayb.nbc_applemarket.model.Location
import com.dothebestmayb.nbc_applemarket.model.User

/**
 * 로그인 시스템이 없기 때문에 로그인 했다는 가정하에 동작하도록 하기 위한 클래스
 */
object LoggedUserManager {
    private var loggedUser = User("배캠이", Location("내배캠동"), 36.5f)

    val locations: LinkedHashSet<Location> = linkedSetOf(Location("내배캠동"), Location("여의도동"))

    init {
        UserManager.addUser(loggedUser)
    }

    fun updateUserInfo(user: User) {
        loggedUser = user
    }

    fun getUserInfo() = loggedUser

    fun getUserLocation(): Location {
        return loggedUser.location
    }
}