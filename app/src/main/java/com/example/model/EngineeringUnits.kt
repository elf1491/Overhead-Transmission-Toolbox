package com.example.model

enum class UnitSystem(val label: String, val lengthUnit: String, val tensionUnit: String, val tempUnit: String) {
    IMPERIAL("Imperial (US)", "ft", "lbf", "°F"),
    METRIC("Metric (SI)", "m", "kN", "°C");

    fun toggle(): UnitSystem = if (this == IMPERIAL) METRIC else IMPERIAL
}

object UnitConverter {
    fun feetToMeters(ft: Double): Double = ft * 0.3048
    fun metersToFeet(m: Double): Double = m / 0.3048

    fun lbfToKiloNewtons(lbf: Double): Double = lbf * 0.00444822
    fun kiloNewtonsToLbf(kn: Double): Double = kn / 0.00444822

    fun fahrenheitToCelsius(f: Double): Double = (f - 32.0) * 5.0 / 9.0
    fun celsiusToFahrenheit(c: Double): Double = (c * 9.0 / 5.0) + 32.0

    fun inchesToMm(inches: Double): Double = inches * 25.4
    fun mmToInches(mm: Double): Double = mm / 25.4

    fun lbPerFtToKgPerM(lbFt: Double): Double = lbFt * 1.48816
    fun kgPerMToLbPerFt(kgM: Double): Double = kgM / 1.48816
}
