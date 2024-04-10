package com.dothebestmayb.nbc_applemarket.data

import com.dothebestmayb.nbc_applemarket.model.User

/**
 * 로그인 시스템이 없기 때문에 로그인 했다는 가정하에 동작하도록 하기 위한 클래스
 */
object LoggedUserManager {
    val loggedUser = User("배캠이", "내배캠동", 36.5f)

    init {
        UserManager.addUser(loggedUser)
    }
}