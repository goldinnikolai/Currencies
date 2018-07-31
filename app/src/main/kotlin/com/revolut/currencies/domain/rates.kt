package com.revolut.currencies.domain

data class Rates(
  val base: String,
  val date: String,
  val rates: Map<String, Double>
)