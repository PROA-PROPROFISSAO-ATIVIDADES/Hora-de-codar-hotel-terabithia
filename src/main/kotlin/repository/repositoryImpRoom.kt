package Hotel.repository

import Hotel.model.ModelGuest
import Hotel.model.ModelRoom

class RepositoryImpRoom : RepositoryRoom{
    val rooms = mutableListOf<ModelRoom>()

    init {
        repeat(20){ index ->
            rooms.add(ModelRoom(index + 1, false))
        }
    }

    override fun listRoom(): List<ModelRoom> {
        return rooms;
    }

    override fun listEmptyRoom(): List<ModelRoom> {
        return listRoom().filter { !it.ocupado }
    }

    override fun save(room: ModelRoom): ModelRoom {
        rooms.add(room)
        return room
    }

    override fun find(roomNumber: Int): ModelRoom? {
        return rooms.find { it.numero == roomNumber }
    }

    override fun setOccupied(roomNumber: Int): ModelRoom? {
        val room = find(roomNumber)
        room?.ocupado = true
        return room
    }

    override fun unsetOccupied(roomNumber: Int): ModelRoom? {
        val room = find(roomNumber)
        room?.ocupado = false
        return room
    }
}