package Hotel.view

import Hotel.controller.ControllerMenu

class ViewMenu(
    private val controller: ControllerMenu
) {
    fun start(){
        println("--------Menu--------")
        while(true){
            val options = controller.toList()
            options.forEachIndexed { index, option -> println("${index+1} - ${option.title}") }
            val input = readln().toIntOrNull()
            val option = input?.let { options.getOrNull(it -1) }
            if(option == null){
                error("Opção Invalida")
                continue
            }

            option.action()
        }
    }

    fun run(title: String){
        println("--------$title--------")
        val options = controller.toList()
        options.forEachIndexed { index, option -> println("${index+1} - ${option.title}") }
        val input = readln().toIntOrNull()
        val option = input?.let { options.getOrNull(it -1) }
        if(option == null){
            error("Opção Invalida")
        }

        option!!.action()
    }

    fun error(message: String){
        println(message)
    }
}