package Hotel.repository

import Hotel.model.ModelEvent

interface RepositoryEvent {
    fun save(event: ModelEvent): ModelEvent
    fun list(): List<ModelEvent>
}