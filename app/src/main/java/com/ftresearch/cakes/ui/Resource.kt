package com.ftresearch.cakes.ui

class Resource<T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}
