package com.kissthinker.trading

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Example output:
  * I'll accept the FIRST received price of 94.40 GBP from JP Morgan
  */
object WebServiceExample1App extends App with System {

  val creditSuisseQuote = CreditSuisse quote "AAPL"

  val jpmorganQuote = JPMorgan quote "AAPL"

  (creditSuisseQuote or jpmorganQuote) map { quote =>
    println(s"I'll accept the FIRST received price of ${quote.price} from ${quote.bank.name}")
  } onComplete { result =>
    if (result.isFailure) println("Ignoring the fact that we have no quote")
    sys.exit()
  }
}