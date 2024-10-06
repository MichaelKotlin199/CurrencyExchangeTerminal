package exception

class WrongOperationTypeException(operationType: String) : AppException("Unknown operation type entered: $operationType")