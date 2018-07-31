package com.revolut.currencies.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.revolut.currencies.CommonAdapter
import com.revolut.currencies.domain.CurrencyModel
import com.revolut.currencies.utils.zip
import org.jetbrains.anko.*
import revolut.com.currencies.R
import java.text.DecimalFormat

class CurrencyHolder(
  val view: View,
  val code: TextView,
  val value: EditText) : RecyclerView.ViewHolder(view)

private val decimalFormat by lazy { DecimalFormat("###.##") }

fun CurrencyHolder.currentValue(): Double =
  value.editableText.toString().toDoubleOrNull() ?: CommonAdapter.DEFAULT_VALUE

fun CurrencyHolder.bind(currency: CurrencyModel, currentValue: Double) {
  code.text = currency.code
  val result = decimalFormat.format(currency.koeff * currentValue)
  value.setText(result.toString())
}

fun CurrencyHolder.bindCopy(currency: CurrencyModel, currentValue: Double) {
  val result = decimalFormat.format(currency.koeff * currentValue)
  value.setText(result.toString())
}

fun Context.currencyHolder(): CurrencyHolder {

  var code: TextView? = null
  var value: EditText? = null

  val view = frameLayout {
    layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
    minimumHeight = dip(50)
    isFocusableInTouchMode = true

    code = textView {
      textSize = 18F
      textColorResource = R.color.colorPrimary
      isClickable = false
    }.lparams {
      width = wrapContent
      height = wrapContent
      gravity = Gravity.START or Gravity.CENTER
      leftMargin = dip(15)
    }

    value = editText {
      lines = 1
      textSize = 16f
      inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }.lparams {
      width = wrapContent
      height = wrapContent
      gravity = Gravity.CENTER or Gravity.END
      rightMargin = dip(15)
    }
  }

  return zip(view, code, value, ::CurrencyHolder)!!
}