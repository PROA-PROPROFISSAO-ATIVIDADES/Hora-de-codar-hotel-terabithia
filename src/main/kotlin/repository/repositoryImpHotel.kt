package Hotel.repository

import Hotel.model.modelHotel

class RepositoryImpHotel(): RepositoryHotel {
    private val hotels = mutableListOf<modelHotel>()

    override fun save(hotel: modelHotel): modelHotel {
        hotels.add(hotel)
        return hotel
    }
}