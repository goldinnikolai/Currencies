package com.revolut.currencies

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import com.revolut.currencies.domain.CurrencyModel
import com.revolut.currencies.domain.defaultCurrency
import com.revolut.currencies.holders.CurrencyHolder
import com.revolut.currencies.holders.bind
import com.revolut.currencies.holders.currencyHolder
import com.revolut.currencies.holders.currentValue
import com.revolut.currencies.utils.diff
import com.revolut.currencies.utils.findPosition
import org.jetbrains.anko.sdk15.listeners.onFocusChange

class CommonAdapter(
  val block: (value: Double, currency: CurrencyModel, currencies: List<CurrencyModel>) -> Unit
) : RecyclerView.Adapter<CurrencyHolder>() {

  companion object {
    const val DEFAULT_VALUE = 1.0
  }

  var currentValue: Double = DEFAULT_VALUE
  var currency: CurrencyModel = defaultCurrency()

  private var textWatcher: TextWatcher? = null

  var currencies: List<CurrencyModel> = emptyList()
    set(value) {
      val diff = DiffUtil.calculateDiff(diff(field, value))
      field = value
      diff.dispatchUpdatesTo(this)
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder =
    parent.context.currencyHolder().also { holder ->
      holder.itemView.setOnClickListener {
        holder.findPosition { position ->

          currentValue = holder.currentValue()
          currency = currencies[position]

          holder.itemView.requestFocus()
        }
      }

      holder.value.onFocusChange { _, hasFocus ->
        when {
          hasFocus -> {
            textWatcher = buildTextWatcher { s ->
              holder.findPosition { position ->
                currentValue = s?.toString()?.toDoubleOrNull() ?: 0.0
                block(currentValue, currencies[position], currencies)
              }
            }
            holder.value.addTextChangedListener(textWatcher)
          }
          else     -> textWatcher?.let { holder.value.removeTextChangedListener(it) }
        }
      }
    }

  override fun onBindViewHolder(holder: CurrencyHolder, position: Int) =
    holder.bind(currencies[position], currentValue)

  override fun getItemCount(): Int =
    currencies.size
}

inline fun buildTextWatcher(crossinline changed: (Editable?) -> Unit): TextWatcher =
  object : TextWatcher {
    override fun afterTextChanged(s: Editable?) = changed(s)
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
  }