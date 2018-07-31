package com.revolut.currencies.api

import com.revolut.currencies.domain.Rates
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
  @GET("latest")
  fun ratesBy(@Query("base") code: String): Call<Rates>
}

class CurrencyApi(val service: CurrencyService)

fun mkApi(): CurrencyApi {

  val retrofit = Retrofit.Builder()
    .baseUrl("https://revolut.duckdns.org")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

  val service = retrofit.create(CurrencyService::class.java)

  return CurrencyApi(service)
}