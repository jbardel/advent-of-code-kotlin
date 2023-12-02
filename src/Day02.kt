fun main() {

    fun createDraw(grab: String): Draw {
        val composition = grab.split(",")

        var r = 0
        var g = 0
        var b = 0

        for (compo in composition) {
            val trimmedB = compo.trim()
            val number = trimmedB.substring(0, trimmedB.indexOf(" ")).toInt()
            val color = trimmedB.substring(trimmedB.indexOf(" ") + 1)

            when (color) {
                "red" -> r = number
                "green" -> g = number
                "blue" -> b = number
            }
        }
        return Draw(r, g, b)
    }

    fun createDraws(line: String): List<Draw> {
        val drawsList = line.split(";")
        return drawsList.map { createDraw(it) }
    }

    fun createGame(line: String): Game {

        val gameNumber = line.substring(line.indexOf(" ") + 1, line.indexOf(":")).toInt()
        val draws = line.substring(line.indexOf(":") + 2)
        return Game(gameNumber, createDraws(draws))
    }

    fun computeScoreGamePart1(game: Game): Int {
        for (draw in game.draws) {
            if (draw.red > TOTAL_CUBES.red || draw.green > TOTAL_CUBES.green || draw.blue > TOTAL_CUBES.blue) {
                return 0
            }
        }
        return game.number
    }

    fun computeScoreGamePart2(game: Game): Int {
        val draws = game.draws
        return draws.maxBy { it.blue }.blue * draws.maxBy { it.green }.green * draws.maxBy { it.red }.red
    }

    fun part1(input: List<String>): Int {
        return input
            .map { createGame(it) }
            .sumOf { computeScoreGamePart1(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { createGame(it) }
            .sumOf { computeScoreGamePart2(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

val TOTAL_CUBES = Draw(12, 13, 14)

data class Draw(val red: Int, val green: Int, val blue: Int)

data class Game(val number: Int, val draws: List<Draw>)