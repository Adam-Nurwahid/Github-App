package com.example.githubfinal.another

import android.view.View

interface Conditions<T> {
    fun onLoading()

    fun onSuccess(data: T)

    fun onFailed(message: String?)

    val gone: Int
        get() = View.GONE

    val visible: Int
        get() = View.VISIBLE
}