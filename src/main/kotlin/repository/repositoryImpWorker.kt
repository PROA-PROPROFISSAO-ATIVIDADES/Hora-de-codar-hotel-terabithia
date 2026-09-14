package Hotel.repository

import Hotel.model.modelWorker

class RepositoryImpWorker : RepositoryWorker {
    val workers = mutableListOf<modelWorker>()

    override fun save(worker: modelWorker): modelWorker {
        workers.add(worker)
        return worker
    }

    override fun find(email: String): modelWorker? {
        return workers.find { it.email == email }
    }
}