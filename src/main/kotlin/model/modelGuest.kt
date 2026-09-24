package Hotel.model

import java.time.LocalDateTime

data class ModelGuest(
    var name: String,
    val creat_at: LocalDateTime = LocalDateTime.now(),
    val id: String = "$name-${creat_at}"
)
