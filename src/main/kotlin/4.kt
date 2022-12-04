import java.io.File
import java.lang.Integer.max
import java.util.stream.IntStream.range
import kotlin.streams.toList

fun main() {
    var counter1 = 0
    var counter2 = 0

    File("input4.txt").forEachLine { line ->
        val (firstRange, secondRange) = line.parseLine()
        counter1 += checkCounter1(firstRange, secondRange)
        counter2 += checkCounter2(firstRange, secondRange)
    }

    println("solution 1: $counter1")
    println("solution 2: $counter2")
}

private fun checkCounter1(firstRange: List<Int>, secondRange: List<Int>): Int {
    val union = firstRange union secondRange
    if (union.size == max(firstRange.size, secondRange.size)) {
        return 1
    }
    return 0
}

private fun checkCounter2(firstRange: List<Int>, secondRange: List<Int>): Int {
    val intersection = firstRange intersect secondRange
    if (intersection.isNotEmpty()) {
        return 1
    }
    return 0
}

private fun String.parseLine(): Pair<List<Int>, List<Int>> {
    val numberRanges = this
        .split(",")
        .map {
            val split = it.split("-")
            range(split[0].toInt(), split[1].toInt() + 1)
                .toList()
        }
    return Pair(numberRanges[0], numberRanges[1])
}