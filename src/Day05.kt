fun main() {

    fun seeds(line: String): List<Long> {
        return line.substringAfter(" ").split(" ").map { it.toLong() }
    }

    fun seeds2(line: String): List<LongRange> {
        return line.substringAfter(" ")
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map { LongRange(it[0], it[0] + it[1] - 1) }
    }

    fun createSoil(line: String, iterator: Iterator<String>): Map<LongRange, LongRange> {
        val ranges = mutableMapOf<LongRange, LongRange>()
        var currentLine = line
        while (iterator.hasNext() && currentLine.isNotEmpty()) {
            val splitted = currentLine.split(" ")
            val dest = splitted[0].toLong()
            val start = splitted[1].toLong()
            val count = splitted[2].toLong()
            ranges[LongRange(start, start + count)] = LongRange(dest, dest + count)
            currentLine = iterator.next()
        }

        return ranges
    }

    fun createSoils(input: List<String>): List<Map<LongRange, LongRange>> {

        val soils = mutableListOf<Map<LongRange, LongRange>>()
        val iterator = input.iterator()

        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.isNotEmpty() && line.first() !in 'a'..'z') {
                soils.add(createSoil(line, iterator))
            }
        }
        return soils
    }

    fun part1(input: List<String>): Long {

        val seeds = seeds(input[0])
        val soils = createSoils(input.subList(2, input.size))

        return seeds.minOf { seed ->
            soils.fold(seed) { acc, map ->
                map.entries.firstOrNull { acc in it.key }?.let { (source, dest) -> dest.first + (acc - source.first) }
                    ?: acc
            }
        }
    }

    fun output(input: LongRange, map: Map<LongRange, LongRange>): List<LongRange> {
        val inputRanges = mutableListOf<LongRange>()
        val outputRanges = mutableListOf<LongRange>()
        for (entry in map.entries) {
            val start = maxOf(entry.key.first, input.first)
            val end = minOf(entry.key.last, input.last)

            if (start <= end) {
                inputRanges += start..end
                outputRanges += (entry.value.first - entry.key.first).let { (start + it)..(end + it) }
            }
        }

        val cuts = listOf(input.first) + inputRanges.flatMap { listOf(it.first, it.last) } + listOf(input.last)
        val unmappedInputRanges = cuts
            .chunked(2)
            .mapNotNull { (first, second) ->
                if (second > first) {
                    if (second == cuts.last()) {
                        first..second
                    } else {
                        first..<second
                    }
                } else {
                    null
                }
            }
        return outputRanges + unmappedInputRanges
    }

    fun part2(input: List<String>): Long {

        var seeds = seeds2(input[0])
        val soils = createSoils(input.subList(2, input.size))
        println(seeds)
        for (soil in soils) {
            seeds = seeds.flatMap { output(it, soil) }
            println(seeds)
        }

        return seeds.minOf { it.first }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    val part2 = part2(testInput)
//    println(part2)
//    check(part2 == 46L)

    val input = readInput("Day05")
    part2(input).println()
}
