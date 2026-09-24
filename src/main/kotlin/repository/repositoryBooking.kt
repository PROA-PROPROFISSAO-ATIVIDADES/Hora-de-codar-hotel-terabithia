package Hotel.repository

import Hotel.model.ModelBooking
import Hotel.model.ModelGuest
import Hotel.model.ModelRoom

interface RepositoryBooking {
    fun book(book: ModelBooking): ModelBooking
}