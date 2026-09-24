package Hotel.model

data class ModelEvent (
    val guests: Int,
    val auditorium: Auditorium,
    val dayWeek: String,
    val hour: Int,
    val company: String,
    val waiterCost: Double,
    val waiters: Int,
    val duration: Int,
    val coffeeLiters: Double,
    val waterLiters: Double,
    val snacks: Int,
    val buffetCost: Double,
    val totalCost: Double,
    val extraChairs: Int = 0
)