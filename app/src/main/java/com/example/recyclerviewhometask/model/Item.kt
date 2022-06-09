package com.example.recyclerviewhometask.model

import androidx.annotation.DrawableRes
import java.io.Serializable

sealed class Item {

    data class Currency(
        var amount: Float = 0f,
        val currency: String = "",
        @DrawableRes val flagDrawableRes: Int = 0
    ) : Item(), Serializable

    data class AddButton(val text: String, @DrawableRes val imageRes: Int) : Item()
}