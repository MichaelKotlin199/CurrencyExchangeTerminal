package data_classes

import kotlin.math.absoluteValue


data class Fraction(val numerator: Int, val denominator: UInt = 1u) {
    init {
        require(denominator != 0u) { "Denominator cannot be zero." }
    }

    private fun gcd(a: UInt, b: UInt): UInt {
        var aVar = a
        var bVar = b
        while (aVar > 0u) {
            val temp = aVar
            aVar = bVar % aVar
            bVar = temp
        }
        return bVar
    }

    private fun simplify(): Fraction {
        val gcdValue = gcd(numerator.absoluteValue.toUInt(), denominator)
        return Fraction(numerator / gcdValue.toInt(), denominator / gcdValue)
    }

    operator fun plus(other: Fraction): Fraction {
        return Fraction(
            numerator * other.denominator.toInt() + other.numerator * denominator.toInt(),
            denominator * other.denominator
        ).simplify()
    }

    operator fun minus(other: Fraction): Fraction {
        return Fraction(
            numerator * other.denominator.toInt() - other.numerator * denominator.toInt(),
            denominator * other.denominator
        ).simplify()
    }

    operator fun times(other: Fraction): Fraction {
        return Fraction(
            numerator * other.numerator,
            denominator * other.denominator
        ).simplify()
    }

    operator fun div(other: Fraction): Fraction {
        return Fraction(
            numerator * other.denominator.toInt(),
            denominator * other.numerator.toUInt()
        ).simplify()
    }

    // Int
    operator fun plus(other: Int): Fraction = this + Fraction(other, 1u)
    operator fun minus(other: Int): Fraction = this - Fraction(other, 1u)
    operator fun times(other: Int): Fraction = this * Fraction(other, 1u)
    operator fun div(other: Int): Fraction = this / Fraction(other, 1u)

    // UInt
    operator fun plus(other: UInt): Fraction = this + Fraction(other.toInt(), 1u)
    operator fun minus(other: UInt): Fraction = this - Fraction(other.toInt(), 1u)
    operator fun times(other: UInt): Fraction = this * Fraction(other.toInt(), 1u)
    operator fun div(other: UInt): Fraction = this / Fraction(other.toInt(), 1u)

    // Long
    operator fun plus(other: Long): Fraction = this + Fraction(other.toInt(), 1u)
    operator fun minus(other: Long): Fraction = this - Fraction(other.toInt(), 1u)
    operator fun times(other: Long): Fraction = this * Fraction(other.toInt(), 1u)
    operator fun div(other: Long): Fraction = this / Fraction(other.toInt(), 1u)

    override fun toString(): String = if (denominator == 1u) "$numerator" else "$numerator/$denominator"
}
