package Hotel

import Hotel.controller.ControllerHotel
import Hotel.controller.ControllerMenu
import Hotel.controller.ControllerWorker
import Hotel.repository.RepositoryImpHotel
import Hotel.repository.RepositoryImpMenu
import Hotel.repository.RepositoryImpWorker
import Hotel.view.ViewHotel
import Hotel.view.ViewMenu
import Hotel.view.ViewWorker
import kotlin.system.exitProcess

val repositoryHotel = RepositoryImpHotel()
val controllerHotel = ControllerHotel(repositoryHotel)
val viewHotel = ViewHotel(controllerHotel)

val repositoryWorker = RepositoryImpWorker()
val controllerWorker = ControllerWorker(repositoryWorker)
val viewWorker = ViewWorker(controllerWorker)

//Esse projeto está sendo desenvolvido de maneira incremental! Nem todos os padrões corretos
// Serão implementadas de primeira, isso porque o exercicio me limita!
// Para evitar fugir do pedido (INPUT - OUTPUT), vou elevando o nivel, conforme atendo os requisitos

fun main() {
    val hotel = controllerHotel.toCreate("Terabithia") //Aqui eu não utilizei a view! Para evitar erros na correção automatizada.
    val worker1 = controllerWorker.toCreate("José", "jose@gmail.com", "2678")

    val auth = viewWorker.login()
    if(auth != true) return

    val repositoryMenu = RepositoryImpMenu()
    val controllerMenu = ControllerMenu(repositoryMenu)
    val viewMenu = ViewMenu(controllerMenu)
    controllerMenu.toCreateOption("Criar Funcionario", viewWorker::createWorker)
    controllerMenu.toCreateOption("Sair", ::sairDoHotel)

    viewMenu.start()
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
    println("------------------------------------------")
    println("Você deseja sair? (S para sim - N para não)")
    val confirma = readln().uppercase()
    when(confirma){
        "S" -> {
            println("Até logo!")
            System.exit(0)
        }
        "N" -> {
            return
        }
        else -> println("Opção Invalida")
    }
}