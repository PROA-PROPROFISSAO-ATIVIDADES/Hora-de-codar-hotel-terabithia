package Hotel.view

import Hotel.repository.RepositoryBooking
import Hotel.repository.RepositoryEvent
import Hotel.repository.RepositoryGuest
import Hotel.repository.RepositoryRoom
import Hotel.utils.formatCurrency

class ViewReport(
    private val bookingRepository: RepositoryBooking,
    private val guestRepository: RepositoryGuest,
    private val eventRepository: RepositoryEvent,
    private val roomRepository: RepositoryRoom
) {
    fun show() {
        val bookings = bookingRepository.list()
        val events = eventRepository.list()
        val occupiedRooms = roomRepository.listRoom().count { it.occupied }
        val lodgingRevenue = bookings.sumOf { it.total }
        val eventRevenue = events.sumOf { it.totalCost }

        println("------------Relatórios Operacionais------------")
        println("Reservas confirmadas: ${bookings.size}")
        println("Taxa de ocupação: ${formatCurrency(occupiedRooms / 20.0 * 100)}%")
        println("Hóspedes cadastrados: ${guestRepository.list().size}")
        println("Eventos confirmados: ${events.size}")
        println("Receita de hospedagem: R$ ${formatCurrency(lodgingRevenue)}")
        println("Receita de eventos: R$ ${formatCurrency(eventRevenue)}")
        println("Receita total: R$ ${formatCurrency(lodgingRevenue + eventRevenue)}")
        if (events.isNotEmpty()) {
            println()
            println("Eventos:")
            events.forEachIndexed { index, event ->
                println(
                    "[${index + 1}] ${event.company} - ${event.auditorium.name}, " +
                        "${event.dayWeek} ${event.hour}h-${event.hour + event.duration}h, " +
                        "${event.guests} convidados, " +
                        "garçons: ${event.waiters}, " +
                        "total: R$ ${formatCurrency(event.totalCost)}"
                )
            }
        }
        println("-----------------------------------------------")
    }
}
