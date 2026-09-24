package Hotel.repository

import Hotel.model.ModelOptionMenu

class RepositoryImpMenu : RepositoryMenu{
    val options = mutableListOf<ModelOptionMenu>()
    override fun createOption(title: String, action: () -> Unit) {
        options.add(ModelOptionMenu(title, action))
    }

    override fun listOptions(): List<ModelOptionMenu> {
        return options
    }
}