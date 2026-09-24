// controller/controllerEvent.kt
package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.Auditorium
import Hotel.model.ModelEvent
import Hotel.repository.RepositoryEvent
import kotlin.math.ceil
import kotlin.math.floor

class ControllerEvent(
    private val repository: RepositoryEvent
) {
    private fun normalizeDay(day: String): String = when (day.trim().lowercase()) {
        "terça" -> "terca"
        "sábado" -> "sabado"
        else -> day.trim().lowercase()
    }

    fun selectAuditorium(guests: Int): ReplyFetch<Auditorium> {
        if (guests !in 0..350) return ReplyFetch(400, "Número de convidados inválido", null)

        val auditors = when {
            guests in 0..150 -> ReplyFetch(200, "Auditório Laranja selecionado", Auditorium.LARANJA)
            guests in 151..220 -> ReplyFetch(200, "Auditório Laranja selecionado", Auditorium.LARANJA)
            else -> ReplyFetch(200, "Auditório Colorado selecionado", Auditorium.COLORADO)
        }
        return auditors
    }

    fun calculateAdditionalChairs(guests: Int): Int {
        return if (guests in (Auditorium.LARANJA.capacity + 1)..(Auditorium.LARANJA.capacity + Auditorium.LARANJA.extraChairs)) {
            guests - Auditorium.LARANJA.capacity
        } else {
            0
        }
    }

    fun validateSchedule(dayInput: String, startHour: Int, duration: Int): ReplyFetch<Unit> {
        val day = normalizeDay(dayInput)
        // segunda a sexta-feira, 07h–23h
        if(day in listOf("segunda", "terca", "quarta", "quinta", "sexta")) {
           if(startHour in 7..23 && duration in 1..12 && (startHour + duration) <= 23) {
               return ReplyFetch(200, "Horário válido ($dayInput às $startHour, duração: $duration horas)", null)
           } else {
               return ReplyFetch(400, "Horário inválido. Segunda a sexta-feira: 07h–23h", null)
           }
        }
        // Sábado e domingo: 07h–15h
        if(day in listOf("sabado", "domingo")) {
            if(startHour in 7..23 && duration in 1..12 && (startHour + duration) <= 15) {
                return ReplyFetch(200, "Horário válido ($dayInput às $startHour, duração: $duration horas)", null)
            } else {
                return ReplyFetch(400, "Horário inválido. Sábado e domingo: 07h–15h", null)
            }
        }
        return ReplyFetch(400, "Dia inválido. Use 'segunda', 'terça', 'quarta', 'quinta', 'sexta', 'sabado' ou 'domingo'.", null)
    }

    fun calculateWaiters(guests: Int, duration: Int): Int {
        val base = ceil(guests / 12.0).toInt()
        val help = floor(duration / 2.0).toInt()
        return base + help
    }

    fun calculateWaiterCost(waiters: Int, duration: Int): Double = waiters * duration * 10.50

    // coffee (0.2), water (0.5), and snacks (7)
    fun calculateBuffet(guests: Int): Triple<Double, Double, Int> =
        Triple(guests * 0.2, guests * 0.5, guests * 7)

    fun calculateBuffetCost(coffee: Double, water: Double, snacks: Int): Double =
        coffee * 0.80 + water * 0.40 + (snacks / 100.0) * 34.00

    fun register(event: ModelEvent): ReplyFetch<ModelEvent> {
        val eventEnd = event.hour + event.duration
        val hasConflict = repository.list().any { existing ->
            normalizeDay(existing.dayWeek) == normalizeDay(event.dayWeek) &&
                existing.auditorium == event.auditorium &&
                event.hour < existing.hour + existing.duration &&
                existing.hour < eventEnd
        }
        if (hasConflict) {
            return ReplyFetch(409, "Já existe um evento nesse auditório e horário.", null)
        }
        repository.save(event)
        return ReplyFetch(201, "Reserva efetuada com sucesso.", event)
    }
}