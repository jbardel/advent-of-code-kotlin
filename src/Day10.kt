fun main() {

    fun findStart(map: List<String>): Pair<Int, Int> {
        for ((idxLigne, ligne) in map.withIndex()) {
            for ((idxChar, char) in ligne.withIndex()) {
                if (char == 'S') {
                    return idxLigne to idxChar
                }
            }
        }
        throw RuntimeException()
    }

    fun neighborhoodStart(map: List<String>, startPosition: Pair<Int, Int>) =
        map.withIndex().map { (idLine, line) ->
            line.withIndex()
                .filter { (idCol, _) ->
                    idLine in startPosition.first - 1..startPosition.first + 1 && idCol in startPosition.second - 1..startPosition.second + 1
                }
                .map { (idCol, _) ->
                    Pair(idLine, idCol)
                }
        }

//
//    fun getMove(c: Char) = when (c) {
//        '|' -> null
//        '-' -> null
//        'L' -> null
//        'J' -> null
//        '7' -> null
//        'F' -> null
//    }

    fun printPosition(map: List<String>, position: Pair<Int, Int>) {
        for (i in position.first - 1..position.first + 1) {
            for (j in position.second - 1..position.second + 1) {
                if (i >= 0 && j >= 0 && i < map.size && j < map[0].length) {
                    print(map[i][j])
                }
            }
            println()
        }
        println()
    }

//    fun loopNeighbors(map: List<String>, position: Pair<Int, Int>, function: (Int, Int, Char) -> Unit) {
//        for (i in position.first - 1..position.first + 1) {
//            for (j in position.second - 1..position.second + 1) {
//                if (i >= 0 && j >= 0 && i < map.size && j < map[0].length) {
//                    function(i, j, map[i][j])
//                }
//            }
//        }
//    }

    fun nextPosition(map: List<String>, currentPosition: Pair<Int, Int>, lastPosition: Pair<Int, Int>) {
        for (i in currentPosition.first - 1..currentPosition.first + 1) {
            for (j in currentPosition.second - 1..currentPosition.second + 1) {
                if (i >= 0 && j >= 0 && i < map.size && j < map[0].length) {
                    val nextPosition = i to j
                    if (map[i][j] != '.' && nextPosition != lastPosition && nextPosition != currentPosition) {
                        println("NEXT POSITION : $nextPosition")
                    }
                }
            }
        }
    }


    fun part1(input: List<String>): Int {
        val start = findStart(input)
        println(start)

        nextPosition(input, Pair(1, 1), Pair(1, 2))

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
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