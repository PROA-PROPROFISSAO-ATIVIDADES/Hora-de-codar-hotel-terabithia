// view/viewEvent.kt
package Hotel.view

import Hotel.controller.ControllerEvent
import Hotel.model.ModelEvent
import Hotel.utils.formatCurrency

class ViewEvent(
    private val controller: ControllerEvent
) {
    fun createEvent(): ModelEvent? {
        println("[Eventos]")
        println("Quantidade de convidados (0 a 350):")
        val guests = readln().toIntOrNull()
        if (guests == null) {
            println("Número de convidados inválido. Informe um número inteiro.")
            return null
        }

        val auditoriumReply = controller.selectAuditorium(guests)
        val auditorium = auditoriumReply.item ?: return null.also { println(auditoriumReply.message) }
        val extraChairs = controller.calculateAdditionalChairs(guests)
        println("Auditório selecionado: ${auditorium.name} ($extraChairs cadeiras adicionais)")

        println("Dia da semana (segunda a sexta, sabado ou domingo):")
        val day = readln()
        println("Hora inicial do evento (número inteiro):")
        val initialHour = readln().toIntOrNull()
        if (initialHour == null) {
            println("Hora inicial inválida. Informe um número inteiro.")
            return null
        }
        println("Duração do evento em horas (1 a 12):")
        val duration = readln().toIntOrNull()
        if (duration == null) {
            println("Duração inválida. Informe um número inteiro entre 1 e 12.")
            return null
        }

        val scheduleReply = controller.validateSchedule(day, initialHour, duration)
        if (scheduleReply.status != 200) { println(scheduleReply.message); return null }

        println("Nome da empresa contratante:")
        val company = readln()
        if (company.isBlank()) {
            println("Nome da empresa inválido.")
            return null
        }

        val waiters = controller.calculateWaiters(guests, duration)
        val costWaiters = controller.calculateWaiterCost(waiters, duration)
        val (coffee, water, snacks) = controller.calculateBuffet(guests)
        val costBuffet = controller.calculateBuffetCost(coffee, water, snacks)
        val total = costWaiters + costBuffet

        println("""
            Empresa: $company
            Auditório: ${auditorium.name}
            Dia: $day
            Horário: $initialHour-${initialHour + duration}h
            Convidados: $guests
            Duração: $duration horas
            Garçons necessários: $waiters
            Custo com garçons: R$ ${formatCurrency(costWaiters)}

            Buffet:
            Café: $coffee L
            Água: $water L
            Salgados: $snacks un
            Custo buffet: R$ ${formatCurrency(costBuffet)}

            Total do evento: R$ ${formatCurrency(total)}
        """.trimIndent())

        println("Confirmar reserva do auditório? (S/N):")
        if (readln().uppercase() != "S") { println("Reserva não efetuada."); return null }

        val event = ModelEvent(
            guests, auditorium, day, initialHour, company, costWaiters, waiters,
            duration, coffee, water, snacks, costBuffet, total, extraChairs
        )
        val reply = controller.register(event)
        println(reply.message)
        return reply.item
    }
}