package Hotel.controller

import Hotel.Reply.replyFetch
import Hotel.model.modelWorker
import Hotel.repository.RepositoryWorker

class ControllerWorker(
    private val repository: RepositoryWorker
) {
    fun toCreate(name: String, email: String, password: String): replyFetch<modelWorker>{
        val worker = modelWorker(name, email, password);
        repository.save(worker);
        return replyFetch<modelWorker>(
            201,
            "Colaborador criado com sucesso!",
            worker
        )
    }

    fun toLogin(email: String, password: String): replyFetch<Boolean>{
        val worker = repository.find(email);

        if(worker === null){
            return replyFetch<Boolean>(
                404,
                "Colaborador não foi encontrado!",
                false
            )
        }

        if(worker.password != password){
            return replyFetch<Boolean>(
                401,
                "Senha incorreta!",
                false
            )
        }

        return replyFetch<Boolean>(
            200,
            "Login realizado com sucesso!",
            true
        )
    }
}