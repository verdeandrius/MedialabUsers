package com.assesment.users.common

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import com.assesment.users.R
import com.facebook.drawee.view.SimpleDraweeView

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

fun EditText.isNotEmpty(errorMessage: String): Boolean {
    return if (text.isEmpty()) {
        error = errorMessage
        false
    } else {
        true
    }
}

fun View.removeForeground() {
    foreground = null
}

fun SimpleDraweeView.setSelected(context: Context) {
    foreground = AppCompatResources.getDrawable(context, R.drawable.stroke_text_view_shape)
}

fun SimpleDraweeView.setUnselected(){
    removeForeground()
}
