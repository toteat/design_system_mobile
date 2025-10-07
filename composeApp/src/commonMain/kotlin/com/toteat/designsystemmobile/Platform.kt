package com.toteat.designsystemmobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform