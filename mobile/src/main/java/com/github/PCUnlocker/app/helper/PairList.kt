package com.github.PCUnlocker.app.helper

/**
 * Created by tim on 28.03.17.
 */
class PairList<A, B> : ArrayList<Pair<A, B>>() {

    fun getListOfFirst(): ArrayList<A> {
        val list: ArrayList<A> = ArrayList()
        forEach { list.add(it.first) }
        return list
    }

    fun getListOfSecond(): ArrayList<B> {
        val list: ArrayList<B> = ArrayList()
        forEach { list.add(it.second) }
        return list
    }

    fun forEachFirst(action: (A) -> Unit) {
        for (item: A in getListOfFirst()) action(item)
    }

    fun forEachSecond(action: (B) -> Unit) {
        for (item: B in getListOfSecond()) action(item)
    }

    fun firstAt(position: Int): A = get(position).first

    fun secondAt(position: Int): B = get(position).second

    fun add(a: A, b: B): Boolean {
        return add(Pair(a, b))
    }

    fun add(index: Int, a: A, b: B) {
        return add(index, Pair(a, b))
    }
}
