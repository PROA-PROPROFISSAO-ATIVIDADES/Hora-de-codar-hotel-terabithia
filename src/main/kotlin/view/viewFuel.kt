// view/viewFuel.kt
package Hotel.view

import Hotel.controller.ControllerFuel
import Hotel.model.ModelGasPoint
import Hotel.utils.formatCurrency

class ViewFuel(
    private val controller: ControllerFuel
) {
    fun compareFuelStations() {
        println("[Abastecimento]")
        val points = listOf("Wayne Oil", "Stark Petrol").map { name ->
            val alcohol = readPositivePrice("$name - informe o preço do álcool por litro:")
            val gasoline = readPositivePrice("$name - informe o preço da gasolina por litro:")
            ModelGasPoint(name, alcohol, gasoline)
        }

        val results = points.map { point ->
            val (fuel, price) = controller.bestFuel(point.priceAlcohol, point.priceGasoline)
            Triple(point.name, fuel, controller.tankCost(price))
        }.sortedBy { it.third }

        results.forEach { (station, fuel, total) ->
            println("$station: melhor opção = $fuel | Total (42 litros) = R$ ${formatCurrency(total)}")
        }

        val (cheapestStation, cheapestFuel, _) = results.first()
        println("É mais barato abastecer com $cheapestFuel no posto $cheapestStation.")
    }

    private fun readPositivePrice(prompt: String): Double {
        while (true) {
            println(prompt)
            val value = readln().toDoubleOrNull()
            if (value != null && value > 0) return value
            println("Preço inválido. Informe um número maior que zero.")
        }
    }
}