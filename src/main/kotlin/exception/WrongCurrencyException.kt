package exception

class WrongCurrencyException(currency: String): AppException("Unknown currency entered: $currency")