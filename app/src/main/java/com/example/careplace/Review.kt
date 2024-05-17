package com.example.careplace

import kotlin.String

data class Review(val content: String = "", val rating: Float = 0f) {
    constructor() : this("", 0f)
}
