package com.dothebestmayb.nbc_applemarket.model

enum class UserTemperature(val temper: Float, val emoji: String, val colorCode: String) {
    VERY_VERY_LOW(0f, "\uD83D\uDE21", "#081f39"),
    VERY_LOW(12.5f, "\uD83D\uDE20", "#868b94"), LOW(30.0f, "\uD83D\uDE10", "#0277b2"),
    NORMAL(36.5f, "\uD83D\uDE42", "#019ceb"), GOOD(50.5f, "\uD83D\uDE00", "#35c898"),
    BETTER(65.5f, "\uD83D\uDE0A", "#f7be68"), EXCELLENT(88.0f, "\uD83D\uDE04", "#fe6e1d");

    companion object {
        fun get(temper: Float): UserTemperature {
            return when {
                temper >= EXCELLENT.temper -> EXCELLENT
                temper >= BETTER.temper -> BETTER
                temper >= GOOD.temper -> GOOD
                temper >= NORMAL.temper -> NORMAL
                temper >= LOW.temper -> LOW
                temper >= VERY_LOW.temper -> VERY_LOW
                else -> VERY_VERY_LOW
            }
        }
    }
}