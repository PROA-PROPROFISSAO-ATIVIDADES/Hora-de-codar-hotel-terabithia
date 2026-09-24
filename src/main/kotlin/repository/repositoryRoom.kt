package Hotel.repository

import Hotel.model.ModelGuest
import Hotel.model.ModelRoom

interface RepositoryRoom {
    fun listRoom(): List<ModelRoom>
    fun listEmptyRoom(): List<ModelRoom>
    fun save(room: ModelRoom): ModelRoom
    fun find(roomNumber: Int): ModelRoom?
    fun setOccupied(roomNumber: Int): ModelRoom?
    fun unsetOccupied(roomNumber: Int): ModelRoom?
}