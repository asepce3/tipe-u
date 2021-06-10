package com.cahyana.asep.tipe_u.data

data class Resource<out T>(
    val status: Status, val data: T?, val exception: Throwable?
) {
    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, null, null)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
        fun <T> error(exception: Throwable?): Resource<T>{
            return Resource(Status.ERROR, null, exception)
        }
    }
}