package Hotel.view

import Hotel.controller.ControllerBooking
import Hotel.controller.ControllerRoom
import Hotel.model.ModelBooking
import Hotel.model.RoomType
import Hotel.utils.formatCurrency
import kotlin.collections.emptyList

class ViewBooking (
    private val controller: ControllerBooking,
    private val controllerRoom: ControllerRoom
) {
    fun createBooking(): ModelBooking? {
        println("-----------------------")
        println("Digite o valor da diária em reais (maior que zero):")
        val dailyRate = readln().toDoubleOrNull()
        if (dailyRate == null || dailyRate <= 0) {
            println("Valor da diária inválido. Informe um número maior que zero.")
            return null
        }

        println("Digite o nome completo do hóspede:")
        val name = readln()
        if (name.isBlank()) {
            println("Nome do hóspede inválido.")
            return null
        }

        lateinit var type: RoomType

        while (true) {
            println("""
                Qual é o tipo do quarto? (S, E ou L)
            """.trimIndent())

            val input = readln()
            val typeData = RoomType.fromChar(input.firstOrNull() ?: ' ')

            if (typeData != null) {
                type = typeData
                break
            }

            println("Opção Inválida")
        }

        val roomNumber = suggestOtherRoom() ?: return null

        println("Digite a data de início da reserva (formato dd-MM-yyyy):")
        val startDate = readln()

        println("Digite a data de término da reserva (formato dd-MM-yyyy):")
        val endDate = readln()

        val preview = controller.previewBooking(roomNumber, name, dailyRate, type, startDate, endDate)
        val booking = preview.item ?: run {
            println(preview.message)
            return null
        }

        println("""
            Resumo:
            Hóspede: ${booking.guest.name}
            Quarto: ${booking.room.number} (${booking.type})
            Subtotal: R$ ${formatCurrency(booking.subtotal)}
            Taxa de serviço (10%): R$ ${formatCurrency(booking.tax)}
            Total: R$ ${formatCurrency(booking.total)}
        """.trimIndent())

        println("Deseja confirmar a reserva? Digite S para sim ou N para não:")
        val confirmation = readln().uppercase()

        if(confirmation == "S"){
            val data = controller.toCreate(roomNumber, name, dailyRate, type, startDate, endDate)
            println(data.message)
            return data.item
        }
        return null
    }

    private fun suggestOtherRoom(): Int? {
        while (true) {
            val livres = controllerRoom.toListFree().item ?: emptyList()
            if (livres.isEmpty()) {
                println("Não há quartos disponíveis.")
                return null
            }
            println("Quartos livres: ${livres.map { it.number }}")
            println("Escolha o número do quarto (1 a 20):")

            val roomNumber = readln().toIntOrNull()
            if(livres.any {it.number == roomNumber}){
                return roomNumber
            }

            println("Quarto já está ocupado ou inválido, escolha outro.")
        }
    }

    fun gridRooms(columns: Int){
        println("------------Mapa-Dos-Quartos----------------")
        println("1--2--3--4--5")
        val rooms = controllerRoom.toListall()
        rooms.item!!.chunked(columns).forEach { row ->
            val textRow = row.joinToString { if (it.occupied) "O" else "L"}
            println(textRow)
        }
        println("---------------------------------------------")
    }

    fun listBookings() {
        println("------------Reservas----------------")
        val list = controller.toList()

        if (list.isEmpty()) {
            println("Nenhuma reserva cadastrada.")
        }

        list.forEachIndexed { index, booking ->
            println("[${index + 1}] ${booking.guest.name} - Quarto: ${booking.room.number} - ${booking.startDate} a ${booking.endDate}")
        }
        println("--------------------------------------")
    }
}
