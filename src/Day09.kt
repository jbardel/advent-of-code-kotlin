fun main() {

    fun allSame(list: List<Int>) = list.all { it == list[0] }

    fun generateDifference(list: List<Int>): List<Int> {
        println("list: $list")
        val newList = mutableListOf<Int>()
        for ((index, v) in list.withIndex()) {
            if (index < list.size - 1) {
                val diff = list[index + 1] - v
                newList.add(diff)
            }
        }
        return newList
    }

    fun a(list: List<Int>): Int {
        if (allSame(list)) return list[0]
        return list.last() + a(generateDifference(list))
    }

    fun b(list: List<Int>): Int {
        if (allSame(list)) return list[0]
        return list.first() - b(generateDifference(list))
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { a(it.split(" ").map { it.toInt() }) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { b(it.split(" ").map { it.toInt() }) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 114)

    val part2 = part2(testInput)
    part2.println()
    check(part2 == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}