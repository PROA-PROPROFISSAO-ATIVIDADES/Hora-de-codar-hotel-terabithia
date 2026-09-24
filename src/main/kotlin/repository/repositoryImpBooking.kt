package Hotel.repository

import Hotel.model.ModelBooking

class RepositoryImpBooking : RepositoryBooking {
    val bookings = mutableListOf<ModelBooking>()

    override fun book(book: ModelBooking): ModelBooking {
        bookings.add(book)
        return book;
    }

    override fun list(): List<ModelBooking> {
        return bookings
    }
}