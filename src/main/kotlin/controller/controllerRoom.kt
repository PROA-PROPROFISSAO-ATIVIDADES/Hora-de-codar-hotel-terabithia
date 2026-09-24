package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.ModelRoom
import Hotel.repository.RepositoryImpRoom

class ControllerRoom(
    private val repository: RepositoryImpRoom
) {
    fun findAvailable(roomNumber: Int): ModelRoom? {
        val room = repository.find(roomNumber)
        return room?.takeIf { !it.occupied }
    }

    fun toCreate(): ReplyFetch<ModelRoom>{
        if (repository.listRoom().size >= 20) {
            return ReplyFetch(400, "O hotel já possui 20 quartos", null)
        }
        val room = ModelRoom(repository.listRoom().size, false)
        val data = repository.save(room)

        return ReplyFetch(
            201,
            "Quarto n${data.number}° com sucesso",
            data
        )
    }

    fun toListall(): ReplyFetch<List<ModelRoom>>{
        val list = repository.listRoom()
        return ReplyFetch(
            200,
            "Sucesso ao listar todos os quartos",
            list
        )
    }

    fun toListFree(): ReplyFetch<List<ModelRoom>>{
        val list = repository.listEmptyRoom()
        return ReplyFetch(
            200,
            "Sucesso ao listar todos os quartos disponiveis",
            list
        )
    }

    fun toCheckIn(roomNumber: Int): ReplyFetch<ModelRoom> {
        val room = repository.find(roomNumber)
        if (room?.occupied == false) {
            repository.setOccupied(roomNumber)
            return ReplyFetch(200, "Sucesso ao reservar quarto", room)
        }
        return ReplyFetch(400, "Quarto já está ocupado", room)
    }

    fun toCheckOut(roomNumber: Int): ReplyFetch<ModelRoom> {
        val room = repository.find(roomNumber)
        if (room?.occupied == true) {
            repository.unsetOccupied(roomNumber)
            return ReplyFetch(200, "Sucesso ao liberar quarto", room)
        }
        return ReplyFetch(400, "O quarto não está sendo usado", room)
    }
}