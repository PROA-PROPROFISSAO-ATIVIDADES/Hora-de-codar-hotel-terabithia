import Hotel.Reply.ReplyFetch
import Hotel.model.ModelGuest
import Hotel.repository.RepositoryGuest

class ControllerGuest(
    private val repository: RepositoryGuest
) {
    fun toCreate(name: String): ReplyFetch<ModelGuest> {
        if (repository.list().size >= 15) {
            return ReplyFetch(400, "Máximo de cadastros atingido", null)
        }
        if (repository.existsByName(name)) {
            return ReplyFetch(400, "Hóspede já cadastrado", null)
        }
        val guest = repository.save(ModelGuest(name))
        return ReplyFetch(201, "Operação realizada com sucesso", guest)
    }

    fun toFind(name: String): ReplyFetch<ModelGuest> {
        return ReplyFetch(200, "Operação realizada com sucesso", repository.find(name))
    }

    fun toFindByPrefix(prefix: String): ReplyFetch<List<ModelGuest>> {
        return ReplyFetch(200, "Operação realizada com sucesso", repository.findByPrefix(prefix))
    }

    fun toList(): ReplyFetch<List<ModelGuest>> {
        return ReplyFetch(200, "Operação realizada com sucesso", repository.list())
    }

    fun toRemove(index: Int): ReplyFetch<ModelGuest> {
        val guest = repository.removeAt(index) ?: return ReplyFetch(404, "Hóspede não encontrado", null)
        return ReplyFetch(200, "Operação realizada com sucesso", guest)
    }

    fun toUpdate(index: Int, newName: String): ReplyFetch<ModelGuest> {
        val guest = repository.updateAt(index, newName) ?: return ReplyFetch(404, "Hóspede não encontrado", null)
        return ReplyFetch(200, "Operação realizada com sucesso", guest)
    }
}