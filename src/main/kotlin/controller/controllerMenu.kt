package Hotel.controller

import Hotel.model.ModelOptionMenu
import Hotel.repository.RepositoryMenu

class ControllerMenu(
    private val repository: RepositoryMenu
) {
    fun toList(): List<ModelOptionMenu>{
        return repository.listOptions()
    }

    fun toCreateOption(title: String, action: () -> Unit){
        repository.createOption(title, action)
    }
}