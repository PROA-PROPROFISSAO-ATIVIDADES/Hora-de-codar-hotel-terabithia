package Hotel.repository

import Hotel.model.ModelEvent

class RepositoryImpEvent : RepositoryEvent {
    val events = mutableListOf<ModelEvent>()
    override fun save(event: ModelEvent): ModelEvent { events.add(event); return event}
    override fun list(): List<ModelEvent> = events
}