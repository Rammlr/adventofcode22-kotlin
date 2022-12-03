import java.io.File

private val enemyMapping = mapOf(Pair("A", 1), Pair("B", 2), Pair("C", 3))
private val ownMapping = mapOf(Pair("X", 1), Pair("Y", 2), Pair("Z", 3))

fun main() {
    var totalScore = 0
    // X = lose, Y = draw, Z = lose

    File("input2.txt").forEachLine { line ->
        val whatToDo = line.split(" ")[1]
        val enemy = line.split(" ")[0]
        val own = evaluateWhatToDo(enemyMapping[enemy]!!, whatToDo)
        val score = evaluateMatch(own, enemyMapping[enemy]!!)
        println("$enemy $whatToDo = $score")
        totalScore += score
    }

    println("total score: $totalScore")
}

private fun evaluateWhatToDo(enemy: Int, whatToDo: String): Int{
    return when(whatToDo){
        "X" -> if(enemy == 1) 3 else enemy - 1
        "Y" -> enemy
        else -> (enemy % 3) + 1
    }
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