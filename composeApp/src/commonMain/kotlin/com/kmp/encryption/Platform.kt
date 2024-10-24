package com.kmp.encryption

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform