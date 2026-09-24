package Hotel.repository

import Hotel.model.ModelHotel

interface RepositoryHotel {
    fun save(hotel: ModelHotel): ModelHotel
}