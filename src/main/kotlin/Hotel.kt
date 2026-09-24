package Hotel

import ControllerGuest
import Hotel.controller.ControllerBooking
import Hotel.controller.ControllerAirConditioning
import Hotel.controller.ControllerEvent
import Hotel.controller.ControllerFuel
import Hotel.controller.ControllerHotel
import Hotel.controller.ControllerMenu
import Hotel.controller.ControllerRoom
import Hotel.controller.ControllerWorker
import Hotel.repository.RepositoryImpBooking
import Hotel.repository.RepositoryImpEvent
import Hotel.repository.RepositoryImpGuest
import Hotel.repository.RepositoryImpHotel
import Hotel.repository.RepositoryImpMenu
import Hotel.repository.RepositoryImpRoom
import Hotel.repository.RepositoryImpWorker
import Hotel.view.ViewAirConditioning
import Hotel.view.ViewBooking
import Hotel.view.ViewEvent
import Hotel.view.ViewFuel
import Hotel.view.ViewGuest
import Hotel.view.ViewMenu
import Hotel.view.ViewReport
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
    controllerMenuGuest.toCreateOption("Buscar hóspede por prefixo", viewGuest::searchByPrefix)
    controllerMenuGuest.toCreateOption("Buscar hóspede", viewGuest::searchByName)
    controllerMenuGuest.toCreateOption("Listar hóspedes", viewGuest::listGuests)
    controllerMenuGuest.toCreateOption("Atualizar hóspede", viewGuest::updateGuest)
    controllerMenuGuest.toCreateOption("Remover hóspede", viewGuest::removeGuest)
    controllerMenuGuest.toCreateOption("Voltar ao menu principal") { return@toCreateOption }

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
    controllerMenuGuest.toCreateOption("Sair") { exitHotel(worker1.item?.name ?: "") }

    //---------------Booking
    val repositoryBooking = RepositoryImpBooking();
    val controllerBooking = ControllerBooking(repositoryBooking, repositoryGuest, controllerRoom);
    val viewBooking = ViewBooking(controllerBooking, controllerRoom);
    val repositoryMenuBooking = RepositoryImpMenu()
    val controllerMenuBooking = ControllerMenu(repositoryMenuBooking)
    val viewMenuBooking = ViewMenu(controllerMenuBooking)

    controllerMenuBooking.toCreateOption("Criar reserva", viewBooking::createBooking)
    controllerMenuBooking.toCreateOption("Listar reservas", viewBooking::listBookings)
    controllerMenuBooking.toCreateOption("Mapa de quartos", { viewBooking.gridRooms(5) })
    controllerMenuBooking.toCreateOption("Voltar ao menu principal") { return@toCreateOption }

    //---------------Event menu
    val repositoryEvent = RepositoryImpEvent()
    val controllerEvent = ControllerEvent(repositoryEvent)
    val viewEvent = ViewEvent(controllerEvent)
    val repositoryMenuEvent = RepositoryImpMenu()
    val controllerMenuEvent = ControllerMenu(repositoryMenuEvent)
    val viewMenuEvent = ViewMenu(controllerMenuEvent)

    controllerMenuEvent.toCreateOption("Criar evento", viewEvent::createEvent)
    controllerMenuEvent.toCreateOption("Voltar ao menu principal") { return@toCreateOption }

    //---------------Fuel menu
    val controllerFuel = ControllerFuel()
    val viewFuel = ViewFuel(controllerFuel)
    val repositoryMenuFuel = RepositoryImpMenu()
    val controllerMenuFuel = ControllerMenu(repositoryMenuFuel)
    val viewMenuFuel = ViewMenu(controllerMenuFuel)

    controllerMenuFuel.toCreateOption("Comparar postos", viewFuel::compareFuelStations)
    controllerMenuFuel.toCreateOption("Voltar ao menu principal") { return@toCreateOption }

    //---------------Air conditioning menu
    val controllerAirConditioning = ControllerAirConditioning()
    val viewAirConditioning = ViewAirConditioning(controllerAirConditioning)
    val repositoryMenuAirConditioning = RepositoryImpMenu()
    val controllerMenuAirConditioning = ControllerMenu(repositoryMenuAirConditioning)
    val viewMenuAirConditioning = ViewMenu(controllerMenuAirConditioning)

    controllerMenuAirConditioning.toCreateOption("Coletar orçamentos", viewAirConditioning::collectBudgets)
    controllerMenuAirConditioning.toCreateOption("Voltar ao menu principal") { return@toCreateOption }

    val viewReport = ViewReport(repositoryBooking, repositoryGuest, repositoryEvent, repositoryRoom)

    //----------------Login
    println("Bem-vindo ao Hotel ${hotel.item?.name}")
    val auth = viewWorker.login()
    if(auth != true) return

    //----------------Menu Global
    println("Bem-vindo ao Hotel ${hotel.item?.name}, ${worker1.item!!.name}. É um imenso prazer ter você por aqui!")
    val repositoryMenu = RepositoryImpMenu()
    val controllerMenu = ControllerMenu(repositoryMenu)
    val viewMenu = ViewMenu(controllerMenu)

    controllerMenu.toCreateOption("Menu reservas de quartos", { viewMenuBooking.run("Menu Reservas") })
    controllerMenu.toCreateOption("Menu hóspedes", { viewMenuGuest.run("Menu Hóspedes") })
    controllerMenu.toCreateOption("Menu eventos", { viewMenuEvent.run("Menu Eventos") })
    controllerMenu.toCreateOption("Menu postos de gasolina", { viewMenuFuel.run("Menu Postos de Gasolina") })
    controllerMenu.toCreateOption("Menu ar-condicionado", { viewMenuAirConditioning.run("Menu Ar-Condicionado") })
    controllerMenu.toCreateOption("Relatórios operacionais", viewReport::show)
    controllerMenu.toCreateOption("Sair") { exitHotel(worker1.item.name) }

    viewMenu.start()
}

fun exitHotel(name: String) {
    println("------------------------------------------")
    println("Você deseja sair? (S para sim - N para não)")
    val confirmation = readln().uppercase()
    when(confirmation){
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