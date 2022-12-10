import java.io.File
import java.util.*

enum class InstructionType(val cycles: Int) {
    NOOP(1), ADDX(2)
}

data class CpuInstruction(val type: InstructionType, val howMany: Int?)

fun main() {
    val instructions = File("input10.txt").readLines().map { line ->
        val split = line.split(" ")
        CpuInstruction(InstructionType.valueOf(split[0].uppercase(Locale.getDefault())), split.getOrNull(1)?.toInt())
    }

    val signalStrengths = mutableMapOf<Int, Int>()
    var cycle = 1
    var registerX = 1
    val crt = mutableListOf<MutableList<Boolean>>()
    crt.init()

    instructions.forEach { instruction ->
        with(instruction) {
            repeat(type.cycles) {
                if ((cycle + 20) % 40 == 0) {
                    signalStrengths[cycle] = cycle * registerX
                }

                val cycleIndex = cycle - 1
                val crtPositionInRow = cycleIndex % 40
                crt[cycleIndex / 40][crtPositionInRow] = crtPositionInRow in (registerX - 1 until registerX + 2)

                cycle++
            }

            when (type) {
                InstructionType.ADDX -> registerX += howMany!!
                InstructionType.NOOP -> {}
            }
        }
    }

    signalStrengths.forEach { signalStrengthEntry ->
        println("${signalStrengthEntry.key}: ${signalStrengthEntry.value}")
    }
    println("sum: ${signalStrengths.values.sum()}")

    println("")
    crt.draw()
}

private fun MutableList<MutableList<Boolean>>.init() {
    for (i in 0 until 6) {
        this.add(mutableListOf())
        for (j in 0 until 40) {
            this[i].add(false)
        }
    }
}

private fun List<List<Boolean>>.draw() {
    this.forEach { row ->
        row.forEach { pixel ->
            val pixelDrawn = if (pixel) "#" else "."
            print(pixelDrawn)
        }
        println()
    }
}
