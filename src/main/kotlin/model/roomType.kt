package Hotel.model

enum class RoomType(val price: Double) {
    Standard(1.00),
    Executivo(1.35),
    Luxo(1.65);

    companion object {
        fun fromChar(letter: Char): RoomType? = when (letter.uppercase()) {
            "S" -> Standard
            "E" -> Executivo
            "L" -> Luxo
            else -> null
        }
    }
}