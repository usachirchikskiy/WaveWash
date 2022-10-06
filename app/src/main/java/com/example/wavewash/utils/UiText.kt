package com.example.wavewash.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

sealed interface ComposeString {

    @Composable
    @ReadOnlyComposable
    fun value(): String

    companion object {
        fun plain(text: String): ComposeString = PlainString(text)

        fun resource(@StringRes resId: Int, vararg params: String): ComposeString = ResourceString(resId, params.toList())

    }

    private data class PlainString(val value: String) : ComposeString {

        @Composable
        @ReadOnlyComposable
        override fun value() = value
    }

    private data class ResourceString(
        @StringRes val resId: Int,
        val params: List<String> = listOf()
    ) : ComposeString {

        @Composable
        @ReadOnlyComposable
        override fun value() = stringResource(resId, params.toTypedArray())
    }
}