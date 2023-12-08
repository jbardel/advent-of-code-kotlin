val regex = "\\w+".toRegex()
fun main() {

    fun createNode(line: String): Pair<String, Pair<String, String>> {
        val matches = regex.findAll(line)
        val groups = matches.toList()
        return groups[0].value to (groups[1].value to groups[2].value)
    }

    fun findFinal(map: Map<String, Pair<String, String>>, pattern: String, start: String): Long {

        var currentKey = start
        var index = 0
        var response = 0L
        while (!currentKey.endsWith("Z")) {
            val direction = pattern[index++]
            currentKey = when (direction) {
                'L' -> map[currentKey]!!.first
                'R' -> map[currentKey]!!.second
                else -> throw RuntimeException()
            }
            if (index == pattern.length) index = 0
            response++
        }

        return response
    }

    fun part1(input: List<String>): Int {

        val pattern = input[0]
        val map = input.subList(2, input.size).associate { createNode(it) }

        var currentKey = "AAA"
        var index = 0
        var response = 0
        while (currentKey != "ZZZ") {
            val direction = pattern[index++]
            currentKey = when (direction) {
                'L' -> map[currentKey]!!.first
                'R' -> map[currentKey]!!.second
                else -> throw RuntimeException()
            }
            if (index == pattern.length) index = 0
            response++
        }

        return response
    }

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }


    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun part2(input: List<String>): Long {

        val pattern = input[0]
        val map = input.subList(2, input.size).associate { createNode(it) }

        return map.keys.filter { it.endsWith("A") }.map { findFinal(map, pattern, it) }
            .reduce { total, next -> lcm(total, next) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 2)


    val testInput2 = readInput("Day08_1_test")
    val part2 = part2(testInput2)
    part2.println()
    check(part2 == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}