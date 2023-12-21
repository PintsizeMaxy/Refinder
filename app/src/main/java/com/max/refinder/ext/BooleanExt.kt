package com.max.refinder.ext

fun Boolean.ifElse(doOnTrue: () -> Unit, doOnFalse: () -> Unit) {
    if(this){
        doOnTrue()
    } else {
        doOnFalse()
    }
}