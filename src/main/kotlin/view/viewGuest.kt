package Hotel.view

import ControllerGuest
import Hotel.model.ModelGuest

class ViewGuest(
    private val controller: ControllerGuest
) {
    fun createGuest(): ModelGuest? {
        println("-----------------------")
        println("Digite o nome completo do hóspede:")
        val name = readln()

        val data = controller.toCreate(name)
        println(data.message)
        return data.item
    }

    fun searchByPrefix() {
        println("Digite o início do nome para pesquisar (prefixo):")
        val prefix = readln()

        val data = controller.toFindByPrefix(prefix)
        val results = data.item ?: emptyList()

        if (results.isEmpty()) {
            println("Hóspede não encontrado")
            return
        }

        println("Resultados:")
        results.forEachIndexed { index, guest ->
            println("[${index + 1}] ${guest.name}")
        }
    }

    fun searchByName() {
        println("Digite o nome completo para pesquisar:")
        val name = readln()

        val data = controller.toFind(name)
        val guest = data.item

        if (guest == null) {
            println("Hóspede não encontrado")
            return
        }

        println("Hóspede encontrado:")
        println("[1] ${guest.name}")
    }

    fun listGuests() {
        println("------------Hóspedes----------------")
        val list = controller.toList().item ?: emptyList()

        if (list.isEmpty()) {
            println("Nenhum hóspede cadastrado.")
        }

        list.forEachIndexed { index, guest ->
            println("[${index + 1}] ${guest.name} - ${guest.createdAt}")
        }
        println("--------------------------------------")
    }

    fun updateGuest() {
        listGuests()
        println("Digite o índice do hóspede que deseja atualizar:")
        val index = (readln().toIntOrNull() ?: 0) - 1

        println("Digite o novo nome completo do hóspede:")
        val newName = readln()

        val data = controller.toUpdate(index, newName)
        println(data.message)
    }

    fun removeGuest() {
        listGuests()
        println("Digite o índice do hóspede que deseja remover:")
        val index = (readln().toIntOrNull() ?: 0) - 1

        val data = controller.toRemove(index)
        println(data.message)
    }
}