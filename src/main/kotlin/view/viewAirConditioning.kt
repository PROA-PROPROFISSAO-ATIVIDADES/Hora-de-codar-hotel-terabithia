// view/viewAirConditioning.kt
package Hotel.view

import Hotel.controller.ControllerAirConditioning
import Hotel.model.ModelBudgetAC
import Hotel.utils.formatCurrency

class ViewAirConditioning(
    private val controller: ControllerAirConditioning
) {
    fun collectBudgets() {
        val budgets = mutableListOf<ModelBudgetAC>()
        while (true) {
            println("Nome da empresa:")
            val company = readln()
            println("Valor do serviço por aparelho em reais (maior que zero):")
            val valueTool = readln().toDoubleOrNull()
            println("Quantidade de aparelhos (maior que zero):")
            val quantity = readln().toIntOrNull()
            println("Percentual de desconto (0 ou maior):")
            val discount = readln().toDoubleOrNull()
            println("Quantidade mínima de aparelhos para aplicar o desconto:")
            val minimum = readln().toIntOrNull()
            println("Valor do deslocamento em reais (0 ou maior):")
            val displacement = readln().toDoubleOrNull()

            if (company.isBlank() || valueTool == null || quantity == null ||
                discount == null || minimum == null || displacement == null ||
                valueTool <= 0 || quantity <= 0 || discount < 0 || minimum < 0 || displacement < 0
            ) {
                println("Dados inválidos. Confira os formatos e informe valores dentro das faixas indicadas.")
                continue
            }

            val budget = controller.addBudget(company, valueTool, quantity, discount, minimum, displacement)
            println("O serviço de ${budget.company} custará R$ ${formatCurrency(budget.total)}")
            budgets.add(budget)

            println("Deseja informar novos dados? (S/N)")
            if (readln().uppercase() != "S" && budgets.size >= 2) break
            if (budgets.size < 2) println("Informe pelo menos duas empresas para comparar os orçamentos.")
        }

        val better = controller.better(budgets) ?: return
        val worst = controller.worst(budgets) ?: return
        val difference = controller.difference(better, worst)

        println("Melhor orçamento: ${better.company} — R$ ${formatCurrency(better.total)}")
        println("Pior orçamento: ${worst.company} — R$ ${formatCurrency(worst.total)}")
        println("Diferença percentual: ${formatCurrency(difference)}%")
    }
}