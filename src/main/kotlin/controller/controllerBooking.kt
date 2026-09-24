package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.ModelBooking
import Hotel.model.ModelGuest
import Hotel.model.RoomType
import Hotel.repository.RepositoryBooking
import Hotel.repository.RepositoryGuest
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
    fun previewBooking(
        roomNumber: Int?,
        guestName: String,
        dailyRate: Double?,
        type: RoomType,
        startDateStr: String,
        endDateStr: String
    ): ReplyFetch<ModelBooking> {
        if (roomNumber == null) return ReplyFetch(400, "Digite um numero valido para o quarto", null)
        if (guestName.isBlank()) return ReplyFetch(400, "Nome inválido", null)
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

        val room = controllerRoom.findAvailable(roomNumber)
            ?: return ReplyFetch(400, "Quarto já está ocupado", null)
        val guest = repositoryGuest.find(guestName) ?: ModelGuest(guestName)
        if (repositoryGuest.find(guestName) == null && repositoryGuest.list().size >= 15) {
            return ReplyFetch(400, "Máximo de cadastros atingido", null)
        }
        val subtotal = dailyRate * days * type.price
        val tax = (subtotal * 10) / 100
        val total = subtotal + tax

        val book = ModelBooking(room, guest, dailyRate, days, type, subtotal, tax, total, startDate, endDate)
        return ReplyFetch(200, "Resumo da reserva", book)
    }

    fun toCreate(
        roomNumber: Int?,
        guestName: String,
        dailyRate: Double?,
        type: RoomType,
        startDateStr: String,
        endDateStr: String
    ): ReplyFetch<ModelBooking> {
        val preview = previewBooking(roomNumber, guestName, dailyRate, type, startDateStr, endDateStr)
        val booking = preview.item ?: return ReplyFetch(preview.status, preview.message, null)

        val checkInReply = controllerRoom.toCheckIn(roomNumber!!)
        val room = checkInReply.item ?: return ReplyFetch(checkInReply.status, checkInReply.message, null)
        val guest = repositoryGuest.find(guestName) ?: repositoryGuest.save(booking.guest)
        val confirmedBooking = booking.copy(room = room, guest = guest)
        repositoryBooking.book(confirmedBooking)
        return ReplyFetch(201, "Reserva feita com sucesso!", confirmedBooking)
    }

    fun toList(): List<ModelBooking> {
        return repositoryBooking.list()
    }
}