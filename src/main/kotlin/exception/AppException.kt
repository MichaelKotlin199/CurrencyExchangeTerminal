package exception

sealed class AppException(message: String) : RuntimeException(message)