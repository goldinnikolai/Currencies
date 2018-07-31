package com.revolut.currencies.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

val moshi by lazy { Moshi.Builder().build() }

inline fun <reified T> moshiAdapter(): JsonAdapter<T> = moshi.adapter()
inline fun <reified T> Moshi.adapter(): JsonAdapter<T> = adapter(T::class.java)