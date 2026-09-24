// controller/controllerFuel.kt
package Hotel.controller

class ControllerFuel {
    fun bestFuel(priceAlcohol: Double, priceGasoline: Double): Pair<String, Double> {
        val ethanolBetter = priceAlcohol <= priceGasoline * 0.70
        return if (ethanolBetter) "Álcool" to priceAlcohol else "Gasolina" to priceGasoline
    }

    fun tankCost(unitPrice: Double): Double = unitPrice * 42
}