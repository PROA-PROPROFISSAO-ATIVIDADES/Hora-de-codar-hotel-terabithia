// model/Auditorium.kt
package Hotel.model

enum class Auditorium(val capacity: Int, val extraChairs: Int) {
    LARANJA(
        150,
        70
    ),
    COLORADO(
        350,
        0
    )
}