package com.kissthinker

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.Try

package object trading {
  implicit class FutureOps[T](val self: Future[T]) {
    def or(other: Future[T]): Future[T] = {
      val p = Promise[T]()

      val completePromise = (t: Try[T]) => p tryComplete t

      self onComplete completePromise
      other onComplete completePromise

      p.future
    }
  }

  def bestBuy(atLeastOneQuote: Quote, quotes: Quote*) = quotes.foldLeft(atLeastOneQuote) { (best, quote) =>
    if (best.price <= quote.price) best else quote
  }
}