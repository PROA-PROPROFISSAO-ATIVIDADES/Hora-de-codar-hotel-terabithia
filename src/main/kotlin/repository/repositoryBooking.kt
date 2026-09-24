package Hotel.repository

import Hotel.model.ModelBooking

interface RepositoryBooking {
    fun book(book: ModelBooking): ModelBooking
    fun list(): List<ModelBooking>
}