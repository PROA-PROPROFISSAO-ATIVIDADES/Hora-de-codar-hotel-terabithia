package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.ModelHotel
import Hotel.model.ModelRoom
import Hotel.repository.RepositoryHotel

class ControllerHotel(
    private val repository: RepositoryHotel
){
    fun toCreate(name: String, rooms: List<ModelRoom>): ReplyFetch<ModelHotel> {
        val hotel = ModelHotel(name, rooms);
        repository.save(hotel);
        return ReplyFetch<ModelHotel>(
            201,
            "Hotel criado com sucesso!",
            hotel
        )
    }
}