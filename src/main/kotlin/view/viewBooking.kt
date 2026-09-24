package Hotel.view

import Hotel.controller.ControllerBooking
import Hotel.controller.ControllerHotel
import Hotel.controller.ControllerRoom
import Hotel.model.ModelBooking
import Hotel.model.ModelHotel
import Hotel.model.RoomType
import kotlin.collections.emptyList

class ViewBooking (
    private val controller: ControllerBooking,
    private val controllerRoom: ControllerRoom
) {
    fun createBooking(): ModelBooking? {
        println("-----------------------")
        println("Digite o valor da diária:")
        val dailyRate = readln().toDoubleOrNull()

        println("Digite o nome completo do hospede:")
        val name = readln()

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

        val roomNumber = suggestOtherRoom()

        println("Digite a data do inicio da reserva no formato: (dd-mm-yyyy")
        val startDate = readln()

        println("Digite a data do fim da reserva no formato: (dd-mm-yyyy")
        val endDate = readln()

        println("Deseja confirmar a reserva? (S/N)")
        val confirmation = readln();

        if(confirmation.uppercase() == "S"){
            val data = controller.toCreate(roomNumber, name, dailyRate, type, startDate, endDate)
            println(data.message)
            return data.item
        }
        return null
    }

    private fun suggestOtherRoom(): Int {
        while (true) {
            val livres = controllerRoom.toListFree().item ?: emptyList()
            println("Quartos livres: ${livres.map { it.numero }}")
            println("Escolha um quarto:")

            val roomNumber = readln().toIntOrNull()
            if(livres.any {it.numero == roomNumber}){
                return roomNumber!!
            }

            println("Quarto já está ocupado ou inválido, escolha outro.")
        }
    }

    fun gridRooms(columns: Int){
        println("------------Mapa-Dos-Quartos----------------")
        println("1--2--3--4--5")
        val rooms = controllerRoom.toListall()
        rooms.item!!.chunked(columns).forEach { row ->
            val textRow = row.joinToString { if (it.ocupado) "O" else "L"}
            println(textRow)
        }
        println("---------------------------------------------")
    }
}
