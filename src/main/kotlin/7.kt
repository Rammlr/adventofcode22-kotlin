import java.io.File

private const val cdPrefix = "$ cd"
private const val lsPrefix = "$ ls"
private const val dirPrefix = "dir"

data class Directory(val name: String, val parentDirectory: Directory? = null) {
    val files = mutableSetOf<Filinger>()
    val subDirectories = mutableSetOf<Directory>()

    val subDirectoriesRecursive: Set<Directory>
        get() = subDirectories + subDirectories.flatMap { it.subDirectoriesRecursive }

    val fileSizes: Long
        get() = files.sumOf { file -> file.fileSize } + subDirectories.sumOf { it.fileSizes }
}

data class Filinger(val name: String, val fileSize: Long, val directory: Directory)

fun main() {
    val rootDirectory = File("input7.txt").readLines().parseCommands()

    printDirectoryRecursive(rootDirectory, 0)
    part1(rootDirectory)
    part2(rootDirectory)
}

private fun printDirectoryRecursive(directory: Directory, currentDepth: Int){
    print("  ".repeat(currentDepth))
    println("- ${directory.name} (dir, size=${directory.fileSizes})")
    directory.subDirectories.forEach { subDirectory ->
        printDirectoryRecursive(subDirectory, currentDepth + 1)
    }

    directory.files.forEach { file ->
        print("  ".repeat(currentDepth + 1))
        println("- ${file.name} (file, size=${file.fileSize})")
    }
}

private fun part1(rootDirectory: Directory) {
    val allDirectories = (rootDirectory.subDirectoriesRecursive + rootDirectory)
    val sum = allDirectories
        .filter { directory -> directory.fileSizes <= 100_000 }
        .sumOf { it.fileSizes }
    println("part 1: $sum")
}

private fun part2(rootDirectory: Directory) {
    val howMuchToDelete = 30000000 - (70000000 - rootDirectory.fileSizes)
    val allDirectories = (rootDirectory.subDirectoriesRecursive + rootDirectory)
    val smallestDirectoryToDelete = allDirectories
        .filter { directory -> directory.fileSizes >= howMuchToDelete }
        .minBy { it.fileSizes }
    println("part 2: ${smallestDirectoryToDelete.fileSizes}")
}

private fun List<String>.parseCommands(): Directory {
    val rootDirectory = Directory("/")
    var currentDirectory = rootDirectory

    this.drop(1)
        .filter { line -> !line.startsWith(lsPrefix) && !line.startsWith(dirPrefix) }
        .forEach { line ->
            if (line.startsWith(cdPrefix)) {
                currentDirectory = currentDirectory.parseCd(line)
            } else {
                // 123 filename
                val split = line.split(" ")
                currentDirectory.files.add(Filinger(split[1], split[0].toLong(), currentDirectory))
            }
        }

    return rootDirectory
}

private fun Directory.parseCd(command: String): Directory {
    if (command.endsWith("..")) {
        return parentDirectory!!
    }

    val newDirectory = Directory(command.removePrefix(cdPrefix), this)
    this.subDirectories.add(newDirectory)
    return newDirectory
}