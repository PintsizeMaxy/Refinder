package com.max.refinder.ext

fun Boolean.isTrue(doOnTrue: () -> Unit, doOnFalse: () -> Unit) {
    if(this){
        doOnTrue()
    } else {
        doOnFalse()
    }
}