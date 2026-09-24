package Hotel.controller

import Hotel.Reply.ReplyFetch
import Hotel.model.ModelWorker
import Hotel.repository.RepositoryWorker

class ControllerWorker(
    private val repository: RepositoryWorker
) {
    fun toCreate(name: String, email: String, password: String): ReplyFetch<ModelWorker>{
        val worker = ModelWorker(name, email, password);
        repository.save(worker);
        return ReplyFetch<ModelWorker>(
            201,
            "Colaborador criado com sucesso!",
            worker
        )
    }

    fun toLogin(email: String, password: String): ReplyFetch<Boolean>{
        val worker = repository.find(email);

        if(worker === null){
            return ReplyFetch<Boolean>(
                404,
                "Colaborador não foi encontrado!",
                false
            )
        }

        if(worker.password != password){
            return ReplyFetch<Boolean>(
                401,
                "Senha incorreta!",
                false
            )
        }

        return ReplyFetch<Boolean>(
            200,
            "Login realizado com sucesso!",
            true
        )
    }
}