package com.revolut.currencies.utils

inline fun <A, B, R> zip(a: A?, b: B?, func: (A, B) -> R): R? =
  zip(a, b, Unit) { a, b, c -> func(a, b) }

inline fun <A, B, C, R> zip(a: A?, b: B?, c: C?, func: (A, B, C) -> R): R? =
  zip(a, b, c, Unit) { a, b, c, d -> func(a, b, c) }

inline fun <A, B, C, D, R> zip(a: A?, b: B?, c: C?, d: D?, func: (A, B, C, D) -> R): R? =
  a?.let {
    b?.let {
      c?.let {
        d?.let {
          func(a, b, c, d)
        }
      }
    }
  }