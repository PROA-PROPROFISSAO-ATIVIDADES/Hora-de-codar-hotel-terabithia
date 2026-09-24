package Hotel.view

import Hotel.controller.ControllerWorker
import Hotel.model.ModelWorker

class ViewWorker (
    private val controller : ControllerWorker
)  {
    fun createWorker(): ModelWorker? {
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
        var attempts = 1;
        repeat(3){

            if(attempts > 1){
                println("chance $attempts/3")
            }
            println("---------LOGIN--------------")
            println("Digite o email: ")
            val email = readln()

            println("Digite a senha:")
            val password = readln()

            val data = controller.toLogin(email, password)

            if(data.status == 200){
                println("Login realizado com sucesso!")
                return data.item
            }

            attempts++
            println(data.message)
        }
        println("Conta bloqueada temporariamente")
        return false
    }
}