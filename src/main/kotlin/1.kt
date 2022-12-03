import java.io.File

fun main() {
    var i = 0
    val elfsWithCalories = mutableMapOf<Int, Int>()
    val file = File("input1.txt")
    file.forEachLine { line ->
        if(line.isEmpty()){
            i++
        }else{
            elfsWithCalories[i] = elfsWithCalories[i]?.plus(line.toInt()) ?: line.toInt()
        }
    }

    println(elfsWithCalories)
    println(elfsWithCalories.maxBy { it.value })
    println(elfsWithCalories.values.sortedDescending().take(3))
    println(elfsWithCalories.values.sortedDescending().take(3).sum())
}