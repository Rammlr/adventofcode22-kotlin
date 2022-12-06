import java.io.File

fun main() {
    val line = File("input6.txt").readLines().single()

    val hit = line.getFirstUniqueSequence(4)
    val hit2 = line.getFirstUniqueSequence(14)

    println("index: ${line.indexOf(hit) + 4}")
    println("index 2: ${line.indexOf(hit2) + 14}")
}

private fun String.getFirstUniqueSequence(size: Int)
    = windowed(size, 1).first { it.toSet().size == it.length }