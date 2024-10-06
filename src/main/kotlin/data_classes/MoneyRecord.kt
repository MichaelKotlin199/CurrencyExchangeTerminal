package data_classes


data class MoneyRecord(var numberOfBasicUnits: UInt = 0u, var numberOfSubunits: UInt = 0u) {
    operator fun plus(other: MoneyRecord): MoneyRecord {
        val sub = (this.numberOfBasicUnits + other.numberOfBasicUnits) * 100u + this.numberOfSubunits + other.numberOfSubunits
        return MoneyRecord(sub / 100u, sub % 100u)
    }

    operator fun minus(other: MoneyRecord): MoneyRecord {
        val sub = (this.numberOfBasicUnits - other.numberOfBasicUnits) * 100u + this.numberOfSubunits - other.numberOfSubunits
        return MoneyRecord(sub / 100u, sub % 100u)
    }

    operator fun compareTo(other: MoneyRecord): Int {
        return when {
            this.numberOfBasicUnits > other.numberOfBasicUnits -> 1
            this.numberOfBasicUnits < other.numberOfBasicUnits -> -1
            this.numberOfSubunits > other.numberOfSubunits -> 1
            this.numberOfSubunits < other.numberOfSubunits -> -1
            else -> 0
        }
    }

    override fun toString(): String {
        return "${numberOfBasicUnits}.${numberOfSubunits}"
    }
}
