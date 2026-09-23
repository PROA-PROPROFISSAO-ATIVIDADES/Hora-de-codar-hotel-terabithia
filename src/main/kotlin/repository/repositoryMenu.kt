package Hotel.repository

import Hotel.model.ModelOptionMenu

interface RepositoryMenu {
    fun createOption(title: String, action: () -> Unit)
    fun listOptions(): List<ModelOptionMenu>
}