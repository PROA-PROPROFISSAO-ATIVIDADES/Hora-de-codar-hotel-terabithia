package Hotel.model

data class ModelOptionMenu(
    val title: String,
    val action: () -> Unit
)