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

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(invisible: Boolean = false) {
    visibility = if (invisible) View.INVISIBLE else View.GONE
}

fun EditText.isNotEmpty(errorMessage: String) : Boolean {
    return if (text.isEmpty()){
        error = errorMessage
        false
    } else {
        true
    }
}