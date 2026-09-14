package Hotel.repository

import Hotel.model.modelHotel

interface RepositoryHotel {
    fun save(hotel: modelHotel): modelHotel
}