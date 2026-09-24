package Hotel.Reply

data class ReplyFetch<T>(
    val status: Int,
    val message: String,
    val item: T? = null
)
