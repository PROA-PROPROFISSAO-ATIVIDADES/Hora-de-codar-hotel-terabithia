package Hotel.model

enum class RoomType(val price: Double) {
    Standard(1.00),
    Executive(1.35),
    Luxury(1.65);

    companion object {
        fun fromChar(letter: Char): RoomType? = when (letter.uppercase()) {
            "S" -> Standard
            "E" -> Executive
            "L" -> Luxury
            else -> null
        }
    }
}