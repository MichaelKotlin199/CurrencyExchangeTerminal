package service.impl

import data_classes.Fraction
import data_classes.Funds
import data_classes.MoneyRecord
import enums.Currency
import service.CurrencyExchangeService

object CurrencyExchangeServiceImpl : CurrencyExchangeService {
    private var funds = mutableMapOf(
        Currency.RUB to Funds(MoneyRecord(1000000u), MoneyRecord(10000u)),
        Currency.USD to Funds(MoneyRecord(), MoneyRecord(1000u)),
        Currency.EUR to Funds(MoneyRecord(), MoneyRecord(1000u)),
        Currency.USD to Funds(MoneyRecord(), MoneyRecord(1000u)),
        Currency.BTC to Funds(MoneyRecord(), MoneyRecord(1u, 5u))
    )

    val exchangeRates: Map<Currency, Map<Currency, Fraction>> = mapOf(
        Currency.RUB to mapOf(
            Currency.USD to Fraction(1, 90u),
            Currency.EUR to Fraction(1, 100u)
        ),
        Currency.USD to mapOf(
            Currency.RUB to Fraction(90, 1u),
            Currency.EUR to Fraction(9, 10u),
            Currency.USDT to Fraction(1),
            Currency.BTC to Fraction(1, 59112u)
        ),
        Currency.EUR to mapOf(
            Currency.RUB to Fraction(100),
            Currency.USD to Fraction(1, 9u)
        ),
        Currency.USDT to mapOf(
            Currency.USD to Fraction(1)
        ),
        Currency.BTC to mapOf(
            Currency.USD to Fraction(59112)
        )
    )

    override fun getFunds(): Map<Currency, Funds> {
        return funds
    }

    override fun exchangeCurrency(fromCurrency: Currency, toCurrency: Currency, amount: MoneyRecord) : Boolean {
        val fromExchangeRates = exchangeRates[fromCurrency] ?: run {
            println("Non-existent currency: $fromCurrency")
            return false
        }
        val coefficient = fromExchangeRates[toCurrency] ?: run {
            println("Non-existent exchange rate from $fromCurrency to $toCurrency")
            return false
        }
        val fromFunds = funds[fromCurrency] ?: run {
            println("Funds for $fromCurrency do not exist")
            return false
        }
        val toFunds = funds[toCurrency] ?: run {
            println("Funds for $toCurrency do not exist")
            return false
        }
        if (fromFunds.userHas < amount) {
            println("You don't have that much money")
            return false
        }

        fromFunds.userHas -= amount
        fromFunds.serviceHas += amount
        val subCount = (amount.numberOfBasicUnits * 100u + amount.numberOfSubunits) * coefficient.numerator.toUInt() / coefficient.denominator
        val newRecord = MoneyRecord(subCount / 100u, subCount % 100u)
        toFunds.userHas += newRecord
        toFunds.serviceHas -= newRecord
        return true
    }
}