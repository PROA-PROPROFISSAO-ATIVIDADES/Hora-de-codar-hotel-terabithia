package Hotel

import Hotel.controller.ControllerHotel
import Hotel.controller.ControllerWorker
import Hotel.model.modelHotel
import Hotel.repository.RepositoryImpHotel
import Hotel.repository.RepositoryImpWorker
import Hotel.view.viewHotel
import Hotel.view.viewWorker

//Esse projeto está sendo desenvolvido de maneira incremental! Nem todos os padrões corretos
// Serão implementadas de primeira, isso porque o exercicio me limita!
// Para evitar fugir do pedido (INPUT - OUTPUT), vou elevando o nivel, conforme atendo os requisitos

fun main() {
    val repositoryHotel = RepositoryImpHotel()
    val controllerHotel = ControllerHotel(repositoryHotel)
    val viewHotel = viewHotel(controllerHotel)

    val hotel = controllerHotel.toCreate("Terabithia") //Aqui eu não utilizei a view! Para evitar erros na correção automatizada.

    val repositoryWorker = RepositoryImpWorker()
    val controllerWorker = ControllerWorker(repositoryWorker)
    val viewWorker = viewWorker(controllerWorker)

    val worker1 = controllerWorker.toCreate("José", "jose@gmail.com", "2678")
    viewWorker.login()

    inicio()
}

fun inicio() {
    print("Bem vindo ao hotel name !\n")
    println("Escolha uma opção:")
    // A varival escolha armazena a opção escolhida pelo usuário.
    // uma variavel local é utilizada apenas dentro da função inicio().
    val escolha = readln().toIntOrNull()
    when (escolha) {
        1 -> cadastrarQuartos()
        2 -> cadastrarHospedes()
        3 -> CadastroHospedesDataClass()
        4 -> AbastecimentoDeAutomoveis()
        5 -> sairDoHotel()
        else -> erro()
    }
}

fun cadastrarQuartos() {

}

fun AbastecimentoDeAutomoveis() {

}

fun erro(){
    println("Por favor, informe um número entre 1 e 4.")
    inicio()
}

fun sairDoHotel() {
    println("Você deseja sair?")
    val confirma = readln().toBoolean()
    if (confirma) {
        println("Até logo!")
    } else {
        inicio()
    }
}