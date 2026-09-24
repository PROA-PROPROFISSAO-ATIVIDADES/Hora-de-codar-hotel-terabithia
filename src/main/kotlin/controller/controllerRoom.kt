package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.ModelGuest
import Hotel.model.ModelRoom
import Hotel.repository.RepositoryImpRoom

class ControllerRoom(
    private val repository: RepositoryImpRoom
) {
    fun toCreate(): ReplyFetch<ModelRoom>{
        val room = ModelRoom(repository.listRoom().size, false)
        val data = repository.save(room)

        return ReplyFetch(
            201,
            "Quarto n${data.numero}° com sucesso",
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

    fun toChekIn(roomNumber: Int): ReplyFetch<ModelRoom> {
        val room = repository.find(roomNumber)
        if (room?.ocupado == false) {
            repository.setOccupied(roomNumber)
            return ReplyFetch(200, "Sucesso ao reservar quarto", room)
        }
        return ReplyFetch(400, "Quarto já está ocupado", room)
    }

    fun toChekOut(roomNumber: Int): ReplyFetch<ModelRoom> {
        val room = repository.find(roomNumber)
        if (room?.ocupado == true) {
            repository.unsetOccupied(roomNumber)
            return ReplyFetch(200, "Sucesso ao liberar quarto", room)
        }
        return ReplyFetch(400, "O quarto não está sendo usado", room)
    }
}