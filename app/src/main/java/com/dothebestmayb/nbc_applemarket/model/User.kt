package com.dothebestmayb.nbc_applemarket.model

data class User(
    val nickname: String,
    val location: String,
    val temper: Float,
) {

    companion object {

        private val dummyData = listOf(
            User("대현동", "서울 서대문구 창천동", 0.0f),
            User("안마담", "인천 계양구 귤현동", 17.5f),
            User("코코유", "수성구 범어동", 32.5f),
            User("Nicole", "해운대구 우제2동", 36.5f),
            User("절명", "연제구 연산제8동", 57.5f),
            User("미니멀하게", "수원시 영통구 원천동", 65.5f),
            User("굿리치", "남구 옥동", 71.2f),
            User("난쉽", "동래구 온천제2동", 89.5f),
            User("알뜰한", "원주시 명륜2동", 99.9f),
            User("똑태현", "중구 동화동", 8.5f),
        )

        fun getDummyData(): List<User> = dummyData
    }
}