import java.io.File

data class MovementInstruction(val direction: Direction, val distance: Int)

enum class Direction(val rowCoefficient: Int, val columnCoefficient: Int) {
    R(0, 1),
    L(0, -1),
    U(-1, 0),
    D(1, 0)
}

fun main() {
    val instructions = File("input9.txt").readLines().map { line ->
        val split = line.split(" ")
        MovementInstruction(Direction.valueOf(split[0]), split[1].toInt())
    }

    val startingPoint = Pair(50000, 50000) // just so we dont have to deal with negative values

    val markedPointsPart1 = mutableSetOf(startingPoint)
    val markedPointsPart2 = mutableSetOf(startingPoint)

    var headPosition = startingPoint.copy()
    var tailPosition = startingPoint.copy()
    val tailPositions = mutableListOf<Pair<Int, Int>>()
    repeat(9) { tailPositions.add(startingPoint.copy()) }

    instructions.forEach { instruction ->
        with(instruction) {
            repeat(distance) {
                headPosition = headPosition.moveOnce(direction)

                tailPosition = simulateTail(headPosition, tailPosition)
                markedPointsPart1.add(tailPosition)

                (0 until tailPositions.size).forEach { index ->
                    tailPositions[index] = simulateTail(
                        tailPositions.getOrElse(index - 1) { headPosition },
                        tailPositions[index],
                    )
                }
                markedPointsPart2.add(tailPositions.last())
            }
        }
    }

    println("visited fields for part 1: ${markedPointsPart1.count()}")
    println("visited fields for part 2: ${markedPointsPart2.count()}")
}

private fun simulateTail(
    headPosition: Pair<Int, Int>,
    tailPosition: Pair<Int, Int>,
): Pair<Int, Int> {
    var newTailPosition = tailPosition
    val distance = distance(headPosition, newTailPosition)
    if (distance >= 2) {
        getDirectionsToMove(newTailPosition, headPosition).forEach { directionToMove ->
            newTailPosition = newTailPosition.moveOnce(directionToMove)
        }
    }
    return newTailPosition
}

private fun getDirectionsToMove(firstPosition: Pair<Int, Int>, secondPosition: Pair<Int, Int>): Set<Direction> {
    val directions = mutableSetOf<Direction>()

    val horizontalDistance = secondPosition.second - firstPosition.second
    if (horizontalDistance > 0) {
        directions.add(Direction.R)
    }
    if (horizontalDistance < 0) {
        directions.add(Direction.L)
    }

    val verticalDistance = secondPosition.first - firstPosition.first
    if (verticalDistance > 0) {
        directions.add(Direction.D)
    }
    if (verticalDistance < 0) {
        directions.add(Direction.U)
    }
    return directions
}

private fun Pair<Int, Int>.moveOnce(direction: Direction): Pair<Int, Int> {
    return Pair(
        first + direction.rowCoefficient,
        second + direction.columnCoefficient
    )
}

private fun distance(firstPosition: Pair<Int, Int>, secondPosition: Pair<Int, Int>): Double {
    return kotlin.math.sqrt(
        ((secondPosition.first - firstPosition.first) * (secondPosition.first - firstPosition.first) +
                (secondPosition.second - firstPosition.second) * (secondPosition.second - firstPosition.second)).toDouble()
    )
}