package com.example.stockmarketcaseapp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.stockmarketcaseapp.R
import com.example.stockmarketcaseapp.model.ValueChange

@BindingAdapter("setVisibility")
fun View.setVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("setValueChangeIndicator")
fun ImageView.setValueChangeIndicator(change: ValueChange) {
    when(change) {
        ValueChange.INCREASED -> {
            setImageResource(R.drawable.value_up)
        }
        ValueChange.SAME -> {
            setImageResource(R.drawable.value_same)
        }
        ValueChange.DECREASED -> {
            setImageResource(R.drawable.value_down)
        }
    }
}

@BindingAdapter("textColorResource")
fun TextView.textColorResource(resource: Int?) {
    if (resource != null) {
        setTextColor(ContextCompat.getColor(context, resource))
    }
}
