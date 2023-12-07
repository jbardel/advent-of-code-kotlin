fun main() {

    fun mapCardValue(c: Char) = when (c) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        else -> c.toString().toInt()
    }

    fun mapCardValue2(c: Char) = when (c) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> -1
        'T' -> 10
        else -> c.toString().toInt()
    }

    fun determineScore(numbers: Map<Int, Int>): Int {
        //card value - count
        val count5 by lazy { numbers.count { it.value == 5 } }
        val count4 by lazy { numbers.count { it.value == 4 } }
        val count3 by lazy { numbers.count { it.value == 3 } }
        val count2 by lazy { numbers.count { it.value == 2 } }

        if (count5 == 1) {
            return 600
        } else if (count4 == 1) {
            return 500
        } else if (count3 == 1 && count2 == 1) {
            return 400
        } else if (count3 == 1 && count2 == 0) {
            return 300
        } else if (count2 == 2) {
            return 200
        } else if (count2 == 1) {
            return 100
        }
        return 0
    }

    fun score(v: Pair<Int, List<Int>>): Int {
        val hand = v.second
        val combinaison = hand.groupBy { it }
            .entries
            .associate { Pair(it.key, it.value.size) }
        return determineScore(combinaison)
    }

    fun score2(v: Pair<Int, List<Int>>): Int {
        val hand = v.second

        if (hand.contains(-1)) {
            return List(14) { it + 1 }.map { j ->
                hand.map { if (it == -1) j else it }
                    .groupBy { it }
                    .entries
                    .associate { Pair(it.key, it.value.size) }
            }.maxOf { determineScore(it) }
        }

        val combinaison = hand.groupBy { it }
            .entries
            .associate { Pair(it.key, it.value.size) }
        return determineScore(combinaison)
    }

    fun part1(input: List<String>): Long {

        return input.map { line ->
            val score = line.substringAfter(' ').toInt()
            val hand = line.take(5)
                .map {
                    mapCardValue(it)
                }
            Pair(score, hand)
        }
            .sortedWith(
                compareBy<Pair<Int, List<Int>>> { score(it) }
                    .thenBy { it.second[0] }
                    .thenBy { it.second[1] }
                    .thenBy { it.second[2] }
                    .thenBy { it.second[3] }
                    .thenBy { it.second[4] }
            )
            .zip(List(input.size) { it + 1 })
            .sumOf {
                val coef = it.second.toLong()
                val score = it.first.first.toLong()
                coef * score
            }
    }

    fun part2(input: List<String>): Long {
        return input.map { line ->
            val score = line.substringAfter(' ').toInt()
            val hand = line.take(5)
                .map {
                    mapCardValue2(it)
                }
            Pair(score, hand)
        }
            .sortedWith(
                compareBy<Pair<Int, List<Int>>> { score2(it) }
                    .thenBy { it.second[0] }
                    .thenBy { it.second[1] }
                    .thenBy { it.second[2] }
                    .thenBy { it.second[3] }
                    .thenBy { it.second[4] }
            )
            .zip(List(input.size) { it + 1 })
            .sumOf {
                val coef = it.second.toLong()
                val score = it.first.first.toLong()
                coef * score
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")

    val part1 = part1(testInput)
    check(part1 == 6440L)

    val part2 = part2(testInput)
    check(part2 == 5905L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}