package com.revolut.currencies.utils

import android.support.v7.util.DiffUtil
import com.revolut.currencies.domain.CurrencyModel

fun diff(old: List<CurrencyModel>, new: List<CurrencyModel>) =
  object : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      old[oldItemPosition].code == new[newItemPosition].code

    override fun getOldListSize(): Int =
      old.size

    override fun getNewListSize(): Int =
      new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      old[oldItemPosition].koeff == new[newItemPosition].koeff
  }