package com.christianalexandre.mlchallengeandroid.modules.util.extensions

fun <K, V> Map <K, V>.take(value: Int): Map<K, V> {
    return this.entries
        .take(value)
        .associate { it.toPair() }
}