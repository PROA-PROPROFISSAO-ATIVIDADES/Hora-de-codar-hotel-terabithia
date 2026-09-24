package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.ModelBooking
import Hotel.model.ModelGuest
import Hotel.model.RoomType
import Hotel.repository.RepositoryBooking
import Hotel.repository.RepositoryGuest
import Hotel.repository.RepositoryImpRoom
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

class ControllerBooking(
    private val repositoryBooking: RepositoryBooking,
    private val repositoryGuest: RepositoryGuest,
    private val controllerRoom: ControllerRoom
) {
    fun toCreate(roomNumber: Int?, guestName: String, dailyRate: Double?, type: RoomType, startDateStr: String, endDateStr: String): ReplyFetch<ModelBooking> {
        if (roomNumber == null) return ReplyFetch(400, "Digite um numero valido para o quarto", null)
        if (dailyRate == null || dailyRate <= 0) return ReplyFetch(400, "Valor Inválido", null)

        val startDate = try {
            LocalDate.parse(startDateStr, formatter)
        } catch (e: DateTimeParseException) {
            return ReplyFetch(400, "Digite uma data no formato 'DD-MM-YYYY'.", null)
        }
        val endDate = try {
            LocalDate.parse(endDateStr, formatter)
        } catch (e: DateTimeParseException) {
            return ReplyFetch(400, "Digite uma data no formato 'DD-MM-YYYY'.", null)
        }

        val days = ChronoUnit.DAYS.between(startDate, endDate)
        if (days !in 1..30) return ReplyFetch(400, "Valor Inválido", null)

        val checkInReply = controllerRoom.toChekIn(roomNumber)
        val room = checkInReply.item ?: return ReplyFetch(checkInReply.status, checkInReply.message, null)

        val guest = repositoryGuest.save(ModelGuest(guestName))
        val subtotal = dailyRate * days * type.price
        val tax = (subtotal * 10) / 100
        val total = subtotal + tax

        val book = ModelBooking(room, guest, dailyRate, days, type, subtotal, tax, total, startDate, endDate)
        repositoryBooking.book(book)
        return ReplyFetch(201, "Reserva feita com sucesso!", book)
    }
}