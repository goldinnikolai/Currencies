package com.revolut.currencies.holders

import android.content.Context
import android.graphics.Rect
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.verticalLayout
import revolut.com.currencies.R

class MainHolder(
  val view: View,
  val toolbar: Toolbar,
  val recyclerView: RecyclerView
)

fun Context.itemDecoration(): RecyclerView.ItemDecoration =
  object : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
      outRect.top = dip(5)
    }
  }

fun Context.mainHolder(): MainHolder {

  var toolbar: Toolbar? = null
  var recyclerView: RecyclerView? = null

  val view = verticalLayout {
    layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

    toolbar = toolbar {
      titleResource = R.string.app_name
    }.lparams(width = matchParent, height = dip(50))

    recyclerView = recyclerView {
      itemAnimator = DefaultItemAnimator()
      layoutManager = LinearLayoutManager(context)
      addItemDecoration(itemDecoration())

    }.lparams(width = matchParent, height = matchParent)
  }

  return MainHolder(view, toolbar!!, recyclerView!!)
}