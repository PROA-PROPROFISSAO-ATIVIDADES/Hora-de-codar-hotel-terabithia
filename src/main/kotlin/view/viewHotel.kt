package Hotel.view

import Hotel.Reply.replyFetch
import Hotel.controller.ControllerHotel
import Hotel.model.modelHotel

class viewHotel(
    private val controller: ControllerHotel
) {
    fun createHotel(): modelHotel? {
        println("-----------------------")
        println("Digite o nome do Hotel:")
        val hotelName = readln()

        val data = controller.toCreate(hotelName)
        println(data.message)
        return data.item
    }
}