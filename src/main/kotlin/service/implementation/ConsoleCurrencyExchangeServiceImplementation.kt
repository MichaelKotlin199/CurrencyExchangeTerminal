package service.implementation

import data_classes.Funds
import data_classes.MoneyRecord
import enums.Currency
import enums.Operation
import exception.AppException
import exception.WrongOperationTypeException
import service.IConsoleService

object ConsoleCurrencyExchangeServiceImplementation : IConsoleService {
    private fun getOperation(operationType: String) : Operation {
        return when (operationType) {
            "1" -> Operation.DisplayFunds
            "2" -> Operation.DisplayExchangeRates
            "3" -> Operation.ExchangeCurrency
            "4" -> Operation.Exit
            else -> throw WrongOperationTypeException(operationType)
        }
    }

    private const val SIZE = 30

    private fun displayFunds() {
        val listOfFunds = CurrencyExchangeServiceImplementation.getFunds()
        println(" ".repeat(SIZE) + "Your".padEnd(SIZE) + "Service".padEnd(SIZE))
        for ((currency: Currency, funds: Funds) in listOfFunds) {
            print("Currency $currency".padEnd(SIZE))
            print("${funds.userHas}".padEnd(SIZE))
            print("${funds.serviceHas}".padEnd(SIZE))
            println()
        }
    }

    private fun displayExchangeRates() {
        val listOfExchangeRates = CurrencyExchangeServiceImplementation.exchangeRates

        print(" ".repeat(SIZE))
        for (currency: Currency in Currency.entries) {
            print("$currency".padEnd(SIZE))
        }
        println()
        for (currency1: Currency in Currency.entries) {
            if (currency1 !in listOfExchangeRates) {
                continue
            }
            print(currency1.toString().padEnd(SIZE))
            for (currency2: Currency in Currency.entries) {
                if (currency2 !in listOfExchangeRates[currency1]!!) {
                    print(" ".repeat(SIZE))
                }
                else {
                    print("${listOfExchangeRates[currency1]!![currency2]}".padEnd(SIZE))
                }
            }
            println()
        }
    }

    private fun getCurrency(currencyString: String): Currency {
        return when (currencyString) {
            "1" -> Currency.RUB
            "2" -> Currency.USD
            "3" -> Currency.EUR
            "4" -> Currency.USDT
            "5" -> Currency.BTC
            else -> throw WrongOperationTypeException(currencyString)
        }
    }

    private fun exchangeCurrency() {
        println("Enter your currencies:\n1. RUB\n2. USD\n3. EUR\n4. USDT\n5. BTC")
        println("Enter from currency:")
        val currency1 = getCurrency(readln())
        println("Enter to currency:")
        val currency2 = getCurrency(readln())
        println("Enter amount:")
        var amount = MoneyRecord()
        val amountString = readln()
        amount = if ('.' in amountString) {
            MoneyRecord(
                amountString.substringBefore('.').toUInt(),
                amountString.substringAfter('.').toUInt()
            )
        } else {
            MoneyRecord(amountString.toUInt())
        }

        if (CurrencyExchangeServiceImplementation.exchangeCurrency(currency1, currency2, amount)) {
            println("Exchange was successful")
        }
    }

    override fun work() {
        while(true) {
            try {
                println("Enter your operation:\n1. Display funds\n2. Display exchange rates\n3. Exchange currency\n4. Exit")
                when (getOperation(readln())) {
                    Operation.DisplayFunds -> displayFunds()
                    Operation.DisplayExchangeRates -> displayExchangeRates()
                    Operation.ExchangeCurrency -> exchangeCurrency()
                    Operation.Exit -> break
                }
            } catch (e: AppException) {
                println("${e.message}")
            } catch (e: Exception) {
                println("An unknown error has occurred")
            }
        }
    }
}