package Hotel.repository

import Hotel.model.ModelHotel

class RepositoryImpHotel(): RepositoryHotel {
    private val hotels = mutableListOf<ModelHotel>()

    override fun save(hotel: ModelHotel): ModelHotel {
        hotels.add(hotel)
        return hotel
    }
}