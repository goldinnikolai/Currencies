package com.revolut.currencies.domain

data class CurrencyModel(val code: String, val koeff: Double)

fun defaultCurrency(): CurrencyModel =
  CurrencyModel(code = "EUR", koeff = 1.0)

fun mkCurrency(pair: Map.Entry<String, Double>): CurrencyModel =
  CurrencyModel(code = pair.key, koeff = pair.value)