import java.util.stream.Collectors

fun main() {

    fun isNumber(c: Char) = c in '0'..'9'

    fun isSymbol(c: Char) = !isNumber(c) && c != '.'

    fun isWildcard(c: Char) = c == '*'

    fun detectWildcard(engine: List<String>, x: Int, y: Int): Position {
        for (j in y - 1..y + 1) {
            for (i in x - 1..x + 1) {
                if (j >= 0 && j < engine.size && i >= 0 && i < engine[y].length) {
                    if (isWildcard(engine[j][i])) return Position(i, j)
                }
            }
        }
        return Position.UNDEFINED_POSITION
    }

    fun detectSymbol(engine: List<String>, x: Int, y: Int): Boolean {
        for (j in y - 1..y + 1) {
            for (i in x - 1..x + 1) {
                if (j >= 0 && j < engine.size && i >= 0 && i < engine[y].length) {
                    if (isSymbol(engine[j][i])) return true
                }
            }
        }
        return false
    }

    fun detectWildcardInGroup(engine: List<String>, x: IntRange, y: Int): Part {

        for (i in x) {
            val wildCardPosition = detectWildcard(engine, i, y)
            if (wildCardPosition != Position.UNDEFINED_POSITION) {
                return Part(x, y, wildCardPosition)
            }
        }

        return Part(x, y, Position.UNDEFINED_POSITION)
    }

    fun detectSymbolInGroup(engine: List<String>, x: IntRange, y: Int): Boolean {
        for (i in x) {
            val symbolDetected = detectSymbol(engine, i, y)
            if (symbolDetected) {
                return true
            }
        }
        return false
    }

    fun searchNumbersInLine(line: String): List<IntRange> {

        var start = -1
        var end = -1
        val l = mutableListOf<IntRange>()

        for ((i, c) in line.withIndex()) {
            if (start == -1) {
                if (isNumber(c)) {
                    start = i
                }
            } else {
                if (!isNumber(line[i])) {
                    end = i - 1
                } else if (i == (line.length - 1)) {
                    end = i
                }
            }

            if (start != -1 && end != -1) {
                l.add(IntRange(start, end))
                start = -1
                end = -1
            }
        }
        return l
    }

    fun part1(engine: List<String>): Int {
        var total = 0
        for ((i, line) in engine.withIndex()) {
            val numbersRange = searchNumbersInLine(line)

            for (numberRange in numbersRange) {
                val symbolPresent = detectSymbolInGroup(engine, numberRange, i)
                if (symbolPresent) {
                    total += line.substring(numberRange).toInt()
                }
            }
        }
        return total
    }

    fun part2(engine: List<String>): Int {

        val l = mutableListOf<Part>()

        for ((i, line) in engine.withIndex()) {
            val numbersRange = searchNumbersInLine(line)

            for (numberRange in numbersRange) {
                l.add(detectWildcardInGroup(engine, numberRange, i))
            }
        }

        val partsGroupedByWildcard = l.stream()
            .filter {
                it.wilcard != Position.UNDEFINED_POSITION
            }
            .collect(Collectors.groupingBy {
                it.wilcard
            })

        return partsGroupedByWildcard.values.stream()
            .filter { it.size == 2 }
            .map { list ->
                list.stream().map { e -> engine[e.y].substring(e.x).toInt() }.reduce(1) { a, b -> a * b }
            }.reduce(0) { a, b -> a + b }
    }


    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)


    val input = readInput("Day03")
    part2(input).println()

}

data class Position(val x: Int, val y: Int) {

    companion object {
        val UNDEFINED_POSITION = Position(-1, -1)
    }

}

data class Part(val x: IntRange, val y: Int, val wilcard: Position) {

}