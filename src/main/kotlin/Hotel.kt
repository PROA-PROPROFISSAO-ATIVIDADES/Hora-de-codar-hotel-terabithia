package Hotel

import ControllerGuest
import Hotel.controller.ControllerBooking
import Hotel.controller.ControllerHotel
import Hotel.controller.ControllerMenu
import Hotel.controller.ControllerRoom
import Hotel.controller.ControllerWorker
import Hotel.repository.RepositoryImpBooking
import Hotel.repository.RepositoryImpGuest
import Hotel.repository.RepositoryImpHotel
import Hotel.repository.RepositoryImpMenu
import Hotel.repository.RepositoryImpRoom
import Hotel.repository.RepositoryImpWorker
import Hotel.view.ViewBooking
import Hotel.view.ViewGuest
import Hotel.view.ViewMenu
import Hotel.view.ViewWorker

fun main() {
    //--------------Guest
    val repositoryGuest = RepositoryImpGuest()
    val controllerGuest = ControllerGuest(repositoryGuest)
    val viewGuest = ViewGuest(controllerGuest)

    //-------------Menu Guest
    val repositoryMenuGuest = RepositoryImpMenu()
    val controllerMenuGuest = ControllerMenu(repositoryMenuGuest)
    val viewMenuGuest = ViewMenu(controllerMenuGuest)

    controllerMenuGuest.toCreateOption("Criar hóspede", viewGuest::createGuest)
    controllerMenuGuest.toCreateOption("Buscar hóspede", viewGuest::searchByName)
    controllerMenuGuest.toCreateOption("Listar hóspedes", viewGuest::listGuests)
    controllerMenuGuest.toCreateOption("Atualizar hóspede", viewGuest::updateGuest)
    controllerMenuGuest.toCreateOption("Remover hóspede", viewGuest::removeGuest)
    controllerMenuGuest.toCreateOption("Voltar ao menu principal") { return@toCreateOption }
    controllerMenuGuest.toCreateOption("Sair") { sairDoHotel("") }

    //--------------Room
    val repositoryRoom = RepositoryImpRoom()
    val controllerRoom = ControllerRoom(repositoryRoom)

    //--------------Hotel
    val repositoryHotel = RepositoryImpHotel()
    val controllerHotel = ControllerHotel(repositoryHotel)
    val hotel = controllerHotel.toCreate("Terabithia", repositoryRoom.rooms)

    //--------------Worker
    val repositoryWorker = RepositoryImpWorker()
    val controllerWorker = ControllerWorker(repositoryWorker)
    val viewWorker = ViewWorker(controllerWorker)
    val worker1 = controllerWorker.toCreate("José", "jose@gmail.com", "2678")

    //---------------Booking
    val repositoryBooking = RepositoryImpBooking();
    val controllerBooking = ControllerBooking(repositoryBooking, repositoryGuest, controllerRoom);
    val viewBooking = ViewBooking(controllerBooking, controllerRoom);

    //----------------Login
    println("Bem-vindo ao Hotel ${hotel.item?.name}")
    val auth = viewWorker.login()
    if(auth != true) return

    //----------------Menu Global
    println("Bem-vindo ao Hotel ${hotel.item?.name}, ${worker1.item!!.name}. É um imenso prazer ter você por aqui!")
    val repositoryMenu = RepositoryImpMenu()
    val controllerMenu = ControllerMenu(repositoryMenu)
    val viewMenu = ViewMenu(controllerMenu)

    controllerMenu.toCreateOption("Criar reserva", viewBooking::createBooking)
    controllerMenu.toCreateOption("Mapa quartos", {viewBooking.gridRooms(5)})
    controllerMenu.toCreateOption("Menu hóspedes", { viewMenuGuest.run("Menu Hóspedes") })
    controllerMenu.toCreateOption("Sair") { sairDoHotel(worker1.item.name) }

    viewMenu.start()
}

fun sairDoHotel(name: String) {
    println("------------------------------------------")
    println("Você deseja sair? (S para sim - N para não)")
    val confirma = readln().uppercase()
    when(confirma){
        "S" -> {
            println("Muito obrigado e até logo, ${name}.")
            System.exit(0)
        }
        "N" -> {
            return
        }
        else -> println("Opção Invalida")
    }
}