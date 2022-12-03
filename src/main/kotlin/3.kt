import java.io.File

fun Char.convertToPriority(): Int {
    return if (isUpperCase()) {
        code - 'A'.code + 27
    } else {
        code - 'a'.code + 1
    }
}

fun main() {
    val uniqueCharacters = mutableListOf<Char>()

    File("input3.txt").forEachLine { line ->
        val firstCompartment = line.subSequence(0, line.length / 2).toSet()
        val secondCompartment = line.subSequence(line.length / 2, line.length).toSet()

        val intersection = firstCompartment.intersect(secondCompartment)
        assert(intersection.size == 1)

        uniqueCharacters.add(intersection.first())
    }

    val priorityValues = uniqueCharacters.map { it.convertToPriority() }
    println("sum: ${priorityValues.sum()}")
}