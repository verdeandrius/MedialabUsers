package com.assesment.users.common

import android.view.View
import android.widget.EditText

fun View.toggleVisibility() {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun EditText.isNotEmpty(errorMessage: String) : Boolean {
    return if (text.isEmpty()){
        error = errorMessage
        false
    } else {
        true
    }
}