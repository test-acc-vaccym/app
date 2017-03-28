package com.gitlab.PCU.PCU.helper

/**
 * Created by tim on 28.03.17.
 */
enum class Errors(val number: Int) {
    RANDOM_OVER_50_TRYS(1);

    fun getString(): String = number.toString()

    fun getErrorString(): String = "Error #" + number.toString()
}
