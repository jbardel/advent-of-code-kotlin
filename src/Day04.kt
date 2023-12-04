import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {

    fun createNumberList(line: String): Set<Int> {
        return line
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
            .toSet()
    }

    fun createCard(line: String): Card {

        val cardNumber = line.substring(line.indexOf(" ") + 1, line.indexOf(":")).trim().toInt()
        val winningNumbers = line.substring(line.indexOf(":") + 1, line.indexOf("|"))
        val hand = line.substring(line.indexOf("|") + 1)
        return Card(cardNumber, createNumberList(winningNumbers), createNumberList(hand))
    }

    fun score(card: Card): Int {

        val count = card.winning.intersect(card.hand).count()
        return if (count == 0) 0 else 2.0.pow((count - 1).toDouble()).roundToInt();
    }

    fun score2(card: Card): Int {
        return card.winning.intersect(card.hand).count()
    }

    fun part1(input: List<String>): Int {

        return input
            .map { createCard(it) }
            .sumOf { score(it) }
    }

    fun part2(input: List<String>): Int {

        val mapCards = input
            .map { createCard(it) }
            .map { Pair(it, 1) }
            .toMutableList()

        for ((i, card) in mapCards.withIndex()) {

            var score = score2(card.first)
            val countCard = card.second

            var j = i + 1
            while (score > 0) {
                if (j < mapCards.size) {
                    mapCards[j] = Pair(mapCards[j].first, mapCards[j].second + countCard)
                }
                j += 1
                score -= 1
            }

        }

        return mapCards.sumOf { it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part2(input).println()
}

data class Card(val id: Int, val winning: Set<Int>, val hand: Set<Int>)
