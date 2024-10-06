package service.implementation

import data_classes.Fraction
import data_classes.Funds
import data_classes.MoneyRecord
import enums.Currency
import service.ICurrencyExchangeService

object CurrencyExchangeServiceImplementation : ICurrencyExchangeService {
    private var funds = mutableMapOf<Currency, Funds>(
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
        if (
            fromCurrency !in CurrencyExchangeServiceImplementation.exchangeRates
            ||
            toCurrency !in CurrencyExchangeServiceImplementation.exchangeRates[fromCurrency]!!
            ) {
            println("non-existent currency exchange $fromCurrency to $toCurrency")
            return false
        }
        if (funds[fromCurrency]!!.userHas < amount) {
            println("you don't have that much money")
            return false
        }

        funds[fromCurrency]!!.userHas -= amount
        funds[fromCurrency]!!.serviceHas += amount
        val coefficient = exchangeRates[fromCurrency]!![toCurrency]
        val subCount = (amount.numberOfBasicUnits * 100u + amount.numberOfSubunits) * coefficient!!.numerator.toUInt() / coefficient.denominator
        val newRecord = MoneyRecord(subCount / 100u, subCount % 100u)
        funds[toCurrency]!!.userHas += newRecord
        funds[toCurrency]!!.serviceHas -= newRecord
        return true
    }
}