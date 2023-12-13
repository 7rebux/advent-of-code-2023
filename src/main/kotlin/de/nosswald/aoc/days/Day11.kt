package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/11
object Day11 : Day<Long>(11, "Cosmic Expansion") {
    private const val GALAXY = '#'
    private const val EXPANDED = 'x'

    private data class Galaxy(val x: Int, val y: Int)

    private fun List<String>.expand(): List<String> {
        return this.map { line ->
            if (line.none { it == GALAXY })
                EXPANDED.toString().repeat(line.length)
            else {
                line
                    .mapIndexed { i, old -> if (this.none { it[i] == GALAXY }) EXPANDED else old }
                    .joinToString("")
            }
        }
    }

    private fun combined(input: List<String>, expansionSize: Long): Long {
        val space = input.expand()
        val galaxies = space.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == GALAXY) Galaxy(x, y) else null
            }
        }

        return galaxies
            .flatMapIndexed { i, a ->
                galaxies
                    .drop(i)
                    .map { b -> countSteps(space, a, b, expansionSize) }
            }
            .sum()
    }

    private fun countSteps(space: List<String>, a: Galaxy, b: Galaxy, expansionSize: Long): Long {
        val xIndices = if (a.x > b.x) a.x - 1 downTo b.x else a.x until b.x
        val yIndices = if (a.y > b.y) a.y - 1 downTo b.y else a.y until b.y
        val xSteps = xIndices.sumOf { x -> if (space[a.y][x] == EXPANDED) expansionSize else 1 }
        val ySteps = yIndices.sumOf { y -> if (space[y][a.x] == EXPANDED) expansionSize else 1 }

        return xSteps + ySteps
    }

    override fun partOne(input: List<String>): Long =
        combined(input, expansionSize = 2)

    override fun partTwo(input: List<String>): Long =
        combined(input, expansionSize = 1_000_000)

    override val partOneTestExamples: Map<List<String>, Long> = mapOf(
        listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....",
        ) to 374
    )

    // I adjusted the output since there is only a test provided for expansionSize=100
    override val partTwoTestExamples: Map<List<String>, Long> = mapOf(
        listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....",
        ) to 82000210
    )
}
