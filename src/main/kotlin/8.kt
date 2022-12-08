import java.io.File

data class Tree(val row: Int, val column: Int, val height: Int, var visible: Boolean = false, var scenicScore: Int = 1)

fun main() {
    val forest = mutableListOf<MutableList<Tree>>()
    var row = 0

    File("input8.txt").forEachLine { line ->
        forest.add(mutableListOf())
        line.forEachIndexed { column, digitChar ->
            forest[row].add(Tree(row, column, digitChar.digitToInt()))
        }
        row++
    }

    val border = forest.getBorder().map { borderTree -> borderTree.apply { visible = true } }
    val flatForest = forest.flatten()

    flatForest.filter { it !in border }.forEach { tree ->
        val allDirections = setOf(
            tree.getAbove(flatForest),
            tree.getBelow(flatForest),
            tree.getLeft(flatForest),
            tree.getRight(flatForest)
        )

        // part 1
        tree.visible = tree.visible
                || (allDirections.map { direction -> direction.maxOf { it.height } } + tree.height).min() < tree.height

        //part 2
        allDirections.forEach { direction ->
            tree.scenicScore *= direction.takeWhile { it.height < tree.height }.count() +
                    if (direction.all { it.height < tree.height }) 0 else 1
        }
    }

    forest.print()
    println("part 1: " + flatForest.count { it.visible })
    println("part 2: " + flatForest.maxOf { it.scenicScore })
}

private fun Tree.getAbove(flatForest: List<Tree>) =
    flatForest.filter { it.column == column && it.row < row }.sortedByDescending { it.row }

private fun Tree.getBelow(flatForest: List<Tree>) =
    flatForest.filter { it.column == column && it.row > row }.sortedBy { it.row }

private fun Tree.getLeft(flatForest: List<Tree>) =
    flatForest.filter { it.column < column && it.row == row }.sortedByDescending { it.column }

private fun Tree.getRight(flatForest: List<Tree>) =
    flatForest.filter { it.column > column && it.row == row }.sortedBy { it.column }

private fun List<List<Tree>>.print() {
    this.forEach { row ->
        row.forEach {
            val visibility = if (it.visible) "1" else "0"
            print("(${it.height}, $visibility)")
        }
        println()
    }
}

private fun List<List<Tree>>.getBorder(): List<Tree> =
    this.flatten().filter { it.column == 0 || it.row == 0 || it.row == this.size - 1 || it.column == this[0].size - 1 }