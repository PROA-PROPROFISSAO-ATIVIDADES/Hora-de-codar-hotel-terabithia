package Hotel.repository

import Hotel.model.ModelBooking
import Hotel.model.ModelGuest
import Hotel.model.ModelRoom

class RepositoryImpBooking : RepositoryBooking {
    val bookings = mutableListOf<ModelBooking>()

    override fun book(book: ModelBooking): ModelBooking {
        bookings.add(book)
        return book;
    }
}