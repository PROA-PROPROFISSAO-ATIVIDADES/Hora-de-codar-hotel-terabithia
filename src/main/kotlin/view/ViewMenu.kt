package Hotel.view

import Hotel.controller.ControllerMenu

class ViewMenu(
    private val controller: ControllerMenu
) {
    fun start(){
        while(true){
            val options = controller.toList()
            options.forEachIndexed { index, option -> println("${index+1} - ${option.title}") }
            val input = readln().toIntOrNull()
            val option = input?.let { options.getOrNull(it -1) }
            if(option == null){
                println("Opção Invalida")
                continue
            }

            option.action()
        }
    }
}