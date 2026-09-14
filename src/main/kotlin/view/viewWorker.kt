package Hotel.view

import Hotel.controller.ControllerWorker
import Hotel.model.modelWorker

class viewWorker(
    private val controller : ControllerWorker
) {
    fun createWorker(): modelWorker? {
        println("-----------------------")
        println("Digite o nome do colaborador:")
        val name = readln()

        println("Digite o email do(a) $name:")
        val email = readln()

        println("Digite a senha do(a) $name:")
        val password = readln()

        val data = controller.toCreate(name, email, password)
        println(data.message)
        return data.item
    }

    fun login(): Boolean? {
        println("-----------------------")
        println("Digite o email: ")
        val email = readln()

        println("Digite a senha:")
        val password = readln()

        val data = controller.toLogin(email, password)
        println(data.message)
        return data.item
    }
}