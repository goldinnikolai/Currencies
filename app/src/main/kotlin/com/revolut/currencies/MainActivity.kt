package com.revolut.currencies

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.revolut.currencies.api.mkApi
import com.revolut.currencies.domain.Rates
import com.revolut.currencies.domain.mkCurrency
import com.revolut.currencies.holders.CurrencyHolder
import com.revolut.currencies.holders.bindCopy
import com.revolut.currencies.holders.mainHolder
import com.revolut.currencies.utils.plus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import revolut.com.currencies.R
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

  private var timer: Timer? = null
  private val api by lazy { mkApi() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val holder = mainHolder()
    setContentView(holder.view)

    val adapter = CommonAdapter { value, currency, currencies ->

      val manager = holder.recyclerView.layoutManager as LinearLayoutManager
      val from = manager.findFirstVisibleItemPosition()
      val to = manager.findLastVisibleItemPosition()

      for (position in from until to) {
        val item = holder.recyclerView.findViewHolderForLayoutPosition(position) as? CurrencyHolder
        val model = currencies[position]
        when (model) {
          currency -> Unit
          else     -> item?.bindCopy(model, value)
        }
      }
    }

    holder.recyclerView.adapter = adapter

    timer = timer(name = "CustomTimer", daemon = true, initialDelay = 1000L, period = 1000L) {
      api.service.ratesBy(adapter.currency.code).enqueue(object : Callback<Rates> {
        override fun onResponse(call: Call<Rates>?, response: Response<Rates>?) {
          response?.body()?.rates?.let {
            adapter.currencies = adapter.currency + it.map { mkCurrency(it) }
          }
        }

        override fun onFailure(call: Call<Rates>?, t: Throwable?) {
          Snackbar.make(holder.view, R.string.error_message, Snackbar.LENGTH_SHORT).show()
        }
      })
    }
  }

  override fun onStop() {
    super.onStop()
    clearTimer()
  }

  private fun clearTimer() {
    timer?.cancel()
    timer = null
  }
}
