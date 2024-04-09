package com.dothebestmayb.nbc_applemarket.util

import java.util.Stack

fun Int.toStringWithComma(): String {
    val sb = StringBuilder()
    val stack = Stack<Int>()
    var num = this
    var length = 0
    while (num > 0) {
        stack.add(num % 10)
        num /= 10
        length++
    }
    while (stack.isNotEmpty()) {
        sb.append(stack.pop())
        if (--length % 3 == 0 && length != 0) {
            sb.append(',')
        }
    }
    return String(sb) + "원"
}