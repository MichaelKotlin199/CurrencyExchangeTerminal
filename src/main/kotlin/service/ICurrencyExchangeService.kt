package service

import data_classes.Funds
import data_classes.MoneyRecord
import enums.Currency

interface ICurrencyExchangeService {
    fun getFunds() : Map<Currency, Funds>
    fun exchangeCurrency(fromCurrency: Currency, toCurrency: Currency, amount: MoneyRecord) : Boolean
}