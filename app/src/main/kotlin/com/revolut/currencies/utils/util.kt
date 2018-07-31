package com.revolut.currencies.utils

import android.support.v7.widget.RecyclerView

operator fun <T> T.plus(collection: Collection<T>): List<T> =
  ArrayList<T>(collection.size + 1)
    .also {
      it.add(this)
      it.addAll(collection)
    }

inline fun <T : RecyclerView.ViewHolder> T.findPosition(crossinline block: (Int) -> Unit) =
  when (adapterPosition) {
    RecyclerView.NO_POSITION -> Unit
    else                     -> block(adapterPosition)
  }