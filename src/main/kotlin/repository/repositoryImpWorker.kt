package Hotel.repository

import Hotel.model.ModelWorker

class RepositoryImpWorker : RepositoryWorker {
    val workers = mutableListOf<ModelWorker>()

    override fun save(worker: ModelWorker): ModelWorker {
        workers.add(worker)
        return worker
    }

    override fun find(email: String): ModelWorker? {
        return workers.find { it.email == email }
    }
}