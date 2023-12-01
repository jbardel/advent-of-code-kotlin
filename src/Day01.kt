val textToNumberMapping = mapOf(
    "one" to "o1e",
    "two" to "t2o",
    "three" to "t3e",
    "four" to "f4r",
    "five" to "f5e",
    "six" to "s6x",
    "seven" to "s7n",
    "eight" to "e8t",
    "nine" to "n9e"
)

fun main() {

    fun getNumbersFromLine(line: String): String {
        val numbers = line.filter { it in '0'..'9' }.fold("") { c1, c2 -> "$c1$c2" }
        return when (numbers.length) {
            0 -> "0"
            1 -> "${numbers.first()}${numbers.first()}"
            else -> "${numbers.first()}${numbers.last()}"
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { getNumbersFromLine(it) }.sumOf { it.toInt() }
    }

    fun nextNumberToReplace(line: String): String {
        return textToNumberMapping
            .map { Pair(line.indexOf(it.key), it) }
            .filter { it.first > -1 }
            .minByOrNull { it.first }?.second?.key ?: ""
    }

    fun replaceWords(line: String): String {
        var l = line
        var word = nextNumberToReplace(l)
        while (word.isNotEmpty()) {
            l = l.replace(word, textToNumberMapping[word]!!)
            word = nextNumberToReplace(l)
        }
        return l
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
//            .onEach { print("$it -> ") }
            .map { replaceWords(it) }
//            .onEach { print("$it -> ") }
            .map { getNumbersFromLine(it) }
//            .onEach { println(it) }
            .map { it.toInt() }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part2(input).println()
}
