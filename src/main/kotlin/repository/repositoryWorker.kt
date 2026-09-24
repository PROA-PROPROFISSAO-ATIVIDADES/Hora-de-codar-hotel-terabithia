package Hotel.repository

import Hotel.model.ModelWorker

interface RepositoryWorker {
    fun save(worker: ModelWorker): ModelWorker
    fun find(email: String): ModelWorker?
}