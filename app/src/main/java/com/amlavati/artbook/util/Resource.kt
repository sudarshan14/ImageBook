package com.amlavati.artbook.util

data class Resource<out T>(
    val status:Status,
    val data:T?,
    val message:String?
) {


    companion object{

        fun<T> success(data:T?):Resource<T>{
            return Resource(Status.SUCCESS,data,null)
        }

        fun<T> error(msg:String,data:T?):Resource<T>{
            return Resource(Status.ERROR,data,msg)
        }

        fun<S> failure(msg: String,data:T?)
    }
}


enum class Status{
    SUCCESS,
    ERROR,
    FAILURE
}