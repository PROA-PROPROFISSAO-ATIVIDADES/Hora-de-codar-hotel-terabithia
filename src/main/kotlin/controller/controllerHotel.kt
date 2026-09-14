package Hotel.controller

import Hotel.Reply.replyFetch
import Hotel.model.modelHotel
import Hotel.repository.RepositoryHotel

class ControllerHotel(
    private val repository: RepositoryHotel
){
    fun toCreate(name: String): replyFetch<modelHotel> {
        val hotel = modelHotel(name);
        repository.save(hotel);
        return replyFetch<modelHotel>(
            201,
            "Hotel criado com sucesso!",
            hotel
        )
    }
}