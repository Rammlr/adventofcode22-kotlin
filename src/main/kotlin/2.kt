import java.io.File

private val enemyMapping = mapOf(Pair("A", 1), Pair("B", 2), Pair("C", 3))
private val ownMapping = mapOf(Pair("X", 1), Pair("Y", 2), Pair("Z", 3))

fun main() {
    var totalScore = 0

    File("input2.txt").forEachLine { line ->
        val enemy = line.split(" ")[0]
        val own = line.split(" ")[1]
        val score = evaluateMatch(ownMapping[own]!!, enemyMapping[enemy]!!)
        totalScore += score
        println("$enemy $own (${enemyMapping[enemy]!!} ${ownMapping[own]!!}) score: $score")
    }

    println("total score: $totalScore")
}

private fun evaluateMatch(own: Int, enemy: Int): Int{
    if(own == enemy){
        return own + 3
    }
    if(own - 1 == enemy % 3){
        return own + 6
    }
    return own
}