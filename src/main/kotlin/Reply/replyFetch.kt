package Hotel.Reply

data class replyFetch<T>(
    val status: Int,
    val message: String,
    val item: T? = null
)
