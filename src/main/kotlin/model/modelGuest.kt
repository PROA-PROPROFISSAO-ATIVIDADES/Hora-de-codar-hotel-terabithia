package Hotel.model

import java.time.LocalDateTime

data class ModelGuest(
    var name: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val id: String = "$name-$createdAt"
)
