package Hotel.model

import java.time.LocalDate
import java.time.LocalDateTime

data class ModelBooking(
    val room: ModelRoom,
    val guest: ModelGuest,
    val dailyRate: Double,
    val dailyRates: Long,
    val type: RoomType,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val id: String = "$guest-$room-$createdAt-"
)