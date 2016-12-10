package com.novraditya.recipee

/**
 * Created by elsennovraditya on 12/6/16.
 */

object RecipeeApp {
    var name: String = ""
    var quality: Boolean = true

    fun print(): String? {
        return "The name is ${name} and the quality is ${if (quality) "good" else "bad"}"
    }

    fun testPrint() {
        val a = print()
    }
}
