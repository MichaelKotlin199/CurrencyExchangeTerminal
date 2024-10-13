package service

import data_classes.Funds
import data_classes.MoneyRecord
import enums.Currency

interface CurrencyExchangeService {
    fun getFunds() : Map<Currency, Funds>
    fun exchangeCurrency(fromCurrency: Currency, toCurrency: Currency, amount: MoneyRecord) : Boolean
}