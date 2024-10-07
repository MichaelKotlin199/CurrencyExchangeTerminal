package exception

class WrongAmountException(amountString: String) : AppException("Wrong amount: $amountString")