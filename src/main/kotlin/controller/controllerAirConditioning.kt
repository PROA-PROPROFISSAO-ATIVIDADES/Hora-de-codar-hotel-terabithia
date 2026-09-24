// controller/controllerAirConditioning.kt
package Hotel.controller

import Hotel.model.ModelBudgetAC

class ControllerAirConditioning {
    private val budgets = mutableListOf<ModelBudgetAC>()

    fun addBudget(company: String, unitValue: Double, quantity: Int, discount: Double, minimum: Int, displacement: Double): ModelBudgetAC {
        val budget = calculateBudget(company, unitValue, quantity, discount, minimum, displacement)
        budgets.add(budget)
        return budget
    }

    fun calculateBudget(company: String, unitValue: Double, quantity: Int, discountPercentage: Double, discountMinimum: Int, displacement: Double): ModelBudgetAC {
        val gross = unitValue * quantity
        val discountValue = if (quantity >= discountMinimum) gross * (discountPercentage / 100) else 0.0
        val total = gross - discountValue + displacement
        return ModelBudgetAC(company, total)
    }

    fun better(budget: List<ModelBudgetAC>) = budget.minByOrNull { it.total }
    fun worst(budget: List<ModelBudgetAC>) = budget.maxByOrNull { it.total }
    fun difference(better: ModelBudgetAC, worst: ModelBudgetAC): Double =
        ((worst.total - better.total) / better.total) * 100

    fun getBudgets(): List<ModelBudgetAC> = budgets
}