package com.gitlab.PCU.PCU

import java.util.*

/**
 * Created by tim on 27.03.17.
 */

object Tools {
    fun generateString(rng: Random, characters: CharRange, length: Int): String {
        val text = CharArray(length)
        var cLength: Int = 0
        characters.forEach { cLength++ }
        for (i in 0..length - 1) {
            text[i] = characters.elementAt(rng.nextInt(cLength - 1))
        }
        return String(text)
    }
}
