package Hotel.repository

import Hotel.model.modelWorker

interface RepositoryWorker {
    fun save(worker: modelWorker): modelWorker
    fun find(email: String): modelWorker?
}