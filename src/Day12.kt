import kotlin.math.pow

data class Row(val springs: String, val groups: List<Int>)

fun main() {

    fun verify(springs: String, groups: List<Int>): Boolean = springs.split(".").filter { it.isNotEmpty() }.zip(groups).all {
        it.first.length == it.second
    }


//    fun possibilities(n: Int): List<String> {
//        for (i in 0..n) {
//            println(Integer.toBinaryString(i))
//        }
//        return mutableListOf()
//    }

    fun resolve(springs: String, groups: List<Int>): Int {

        print(springs)
        println(groups)
        println("-------------")
        val indexes = springs.withIndex().filter { it.value == '?' }.map { it.index }
        val possibilities = 2.0.pow(indexes.size).toInt()
        var count = 0
        for (i in 0 until possibilities) {
            val p = i.toString(2).padStart(indexes.size, '0')
            val str = buildString {
                append(springs)
                for ((idx, value) in indexes.withIndex()) {
                    if (p[idx] == '1') {
                        replace(value, value + 1, "#")
                    } else {
                        replace(value, value + 1, ".")
                    }
                }
            }
            if (verify(str, groups)) count += 1
            print("TEST : $str ")
            println(verify(str, groups))
        }
        println("Count $count")
        println("-------------")
        return count
    }

    fun part1(input: List<String>): Int {

//        verify(".###.......#", listOf(3,2,1))
//        return 0

        return input.map { l ->
            l.split(" ")
                .let { Pair(it[0], it[1].split(",").map { n -> n.toInt() }) }
        }
            .sumOf { resolve(it.first, it.second) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    val part1 = part1(testInput)
    part1.println()
//    check(part1 == 1)

//    val testInput2 = readInput("Day10_1_test")
//    val part2 = part1(testInput2)
//    part2.println()

//    val input = readInput("Day10")
//    part1(input).println()
//    part2(input).println()
}