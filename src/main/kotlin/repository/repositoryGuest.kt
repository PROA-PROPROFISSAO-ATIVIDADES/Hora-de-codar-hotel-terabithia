package Hotel.repository

import Hotel.model.ModelGuest

interface RepositoryGuest {
    fun save(guest: ModelGuest): ModelGuest
    fun find(name: String): ModelGuest?
    fun findByPrefix(prefix: String): List<ModelGuest>
    fun existsByName(name: String): Boolean
    fun list(): List<ModelGuest>
    fun removeAt(index: Int): ModelGuest?
    fun updateAt(index: Int, newName: String): ModelGuest?
}