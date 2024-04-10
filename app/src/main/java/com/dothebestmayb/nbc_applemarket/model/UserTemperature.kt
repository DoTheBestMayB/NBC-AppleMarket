package com.dothebestmayb.nbc_applemarket.model

enum class UserTemperature(val temper: Double, val emoji: String, val colorCode: String) {
    VERY_LOW(12.5, "\uD83D\uDE20", "#868b94"), LOW(30.0, "\uD83D\uDE10", "#0277b2"),
    NORMAL(36.5, "\uD83D\uDE42", "#019ceb"), GOOD(50.5, "\uD83D\uDE00", "#35c898"),
    BETTER(65.5, "\uD83D\uDE0A", "#f7be68"), EXCELLENT(88.0, "\uD83D\uDE04", "#fe6e1d")
}