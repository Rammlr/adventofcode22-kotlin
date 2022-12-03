import java.io.File

fun main() {
    val badges = mutableListOf<Char>()

    val lines = File("input3.txt").readLines()

    lines.map { it.toSet() }.chunked(3).forEach { threeLines ->
        val intersection = threeLines[0].intersect(threeLines[1]).intersect(threeLines[2])
        assert(intersection.size == 1)
        badges.add(intersection.first())
    }

    val priorityValues = badges.map { it.convertToPriority() }
    println("sum: ${priorityValues.sum()}")
}