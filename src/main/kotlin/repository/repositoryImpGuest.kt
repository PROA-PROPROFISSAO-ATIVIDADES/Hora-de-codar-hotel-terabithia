package Hotel.repository

import Hotel.model.ModelGuest

class RepositoryImpGuest : RepositoryGuest {
    val guests = mutableListOf<ModelGuest>()

    override fun save(guest: ModelGuest): ModelGuest {
        guests.add(guest)
        guests.sortBy { it.name }
        return guest
    }

    override fun find(name: String): ModelGuest? {
        return guests.find { it.name.equals(name, ignoreCase = true) }
    }

    override fun findByPrefix(prefix: String): List<ModelGuest> {
        return guests.filter { it.name.startsWith(prefix, ignoreCase = true) }
    }

    override fun existsByName(name: String): Boolean {
        return guests.any { it.name.equals(name, ignoreCase = true) }
    }

    override fun list(): List<ModelGuest> {
        return guests
    }

    override fun removeAt(index: Int): ModelGuest? {
        if (index !in guests.indices) return null
        return guests.removeAt(index)
    }

    override fun updateAt(index: Int, newName: String): ModelGuest? {
        if (index !in guests.indices) return null
        val guest = guests[index]
        guest.name = newName
        guests.sortBy { it.name }
        return guest
    }
}