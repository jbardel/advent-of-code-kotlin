import kotlin.math.*

fun main() {


    fun evaluate(pair: Pair<Long, Long>): Int {
        val d = (pair.first * pair.first - 4 * pair.second).toDouble()
        val pos1: Double = (pair.first + sqrt(d)) / 2.0
        val pos2: Double = (pair.first - sqrt(d)) / 2.0

        return if (pos1 < pos2) {
            (floor(pos1 + 1).toInt()..ceil(pos2 - 1).toInt()).count()
        } else {
            (floor(pos2 + 1).toInt()..ceil(pos1 - 1).toInt()).count()
        }
    }


    fun part1(input: List<String>): Int {
        val time = input[0].substringAfter(":").trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        val distance = input[1].substringAfter(":").trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

        return time.zip(distance).map {
            evaluate(it)
        }.reduce { a, b -> a * b }
    }

    fun part2(input: List<String>): Int {
        val time =
            input[0].substringAfter(":").trim().split(" ").filter { it.isNotEmpty() }.reduce { a, b -> a + b }.toLong()
        val distance =
            input[1].substringAfter(":").trim().split(" ").filter { it.isNotEmpty() }.reduce { a, b -> a + b }.toLong()

        return evaluate(Pair(time,distance))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
//    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}