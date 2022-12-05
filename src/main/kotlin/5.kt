import java.io.File

data class Instruction(val howMany: Int, val from: Int, val to: Int) {
    fun execute(
        currentState: List<List<Char>>,
        crateMover9001: Boolean = false,
    ) = with(this) {
        val newState = currentState.clone()
        val cratesToBeMoved = currentState[from].subList(0, howMany)
        newState[from] = currentState[from].drop(cratesToBeMoved.size).toMutableList()
        newState[to].addAll(0, cratesToBeMoved.takeIf { !crateMover9001 }?.reversed() ?: cratesToBeMoved)
        newState
    }
}

fun main() {
    val (instructionLines, initialStateLines) = File("input5.txt")
        .readLines()
        .partition { line -> line.startsWith("move") }

    val initialState = parseInitialState(initialStateLines.dropLast(1)) // last one is an empty line
    val instructions = instructionLines.map { line -> parseInstruction(line) }

    println("initial state: $initialState")
    println("instructions: $instructions")

    var currentState1 = initialState
    var currentState2 = initialState

    instructions.forEach { instruction ->
        currentState1 = instruction.execute(currentState1)
        currentState2 = instruction.execute(currentState2, crateMover9001 = true)
    }

    println("final state for 1: $currentState1")
    println("top of each stack for 1: ${currentState1.map { it.first() }.joinToString("")}")
    println("final state for 2: $currentState2")
    println("top of each stack for 2: ${currentState2.map { it.first() }.joinToString("")}")
}

private fun <T> List<List<T>>.clone() = this.map { it.toMutableList() }.toMutableList()

private fun parseInstruction(instructionLine: String): Instruction {
    val split = instructionLine.removePrefix("move ").split(" ")
    // - 1 to make from and to index friendly
    return Instruction(split[0].toInt(), split[2].toInt() - 1, split[4].toInt() - 1)
}

private fun parseInitialState(initialStateLines: List<String>): MutableList<MutableList<Char>> {
    val initialState = mutableListOf<MutableList<Char>>()
    initialStateLines.last().trim().split("  ").forEach { _ ->
        initialState.add(mutableListOf())
    }

    initialStateLines.dropLast(1).forEach { line ->
        line.chunked(4).forEachIndexed { index, string ->
            string.takeIf { it.isNotBlank() }
                ?.let { crateString -> initialState[index].add(crateString.single { it.isLetter() }) }
        }
    }

    return initialState
}