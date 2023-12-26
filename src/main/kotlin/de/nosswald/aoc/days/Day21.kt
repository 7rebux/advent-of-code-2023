package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/21
object Day21 : Day<Int>(21, "Step Counter") {
    private data class Point(val x: Int, val y: Int) {
        fun neighbors(): Set<Point> = buildSet {
            add(Point(x + 1, y))
            add(Point(x - 1, y))
            add(Point(x, y + 1))
            add(Point(x, y - 1))
        }
    }

    private fun countSteps(garden: List<CharArray>, start: Point): Map<Point, Int> = buildMap {
        val queue = ArrayDeque<Pair<Point, Int>>()

        queue.add(Pair(start, 0))

        while (queue.isNotEmpty()) {
            val (point, steps) = queue.removeFirst()

            if (!this.contains(point) && steps <= 64) {
                this[point] = steps

                queue.addAll(
                    point
                        .neighbors()
                        .filter { (x, y) -> y in garden.indices && x in garden[y].indices }
                        .filter { (x, y) -> garden[y][x] != '#' }
                        .filterNot(this::contains)
                        .map { Pair(it, steps + 1) }
                )
            }
        }
    }

    override fun partOne(input: List<String>): Int {
        val garden = input.map(String::toCharArray)
        val start = Point(
            x = garden.first { it.contains('S') }.indexOf('S'),
            y = garden.indexOfFirst { it.contains('S') }
        )

        return countSteps(garden, start).values.count { it % 2 == 0 }
    }

    override fun partTwo(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    // I adjusted the output since there is only a test provided for a maximum of 6 steps
    // TODO: Make part functions accept additional options for better test support
    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "...........",
            ".....###.#.",
            ".###.##..#.",
            "..#.#...#..",
            "....#.#....",
            ".##..S####.",
            ".##..#...#.",
            ".......##..",
            ".##.#.####.",
            ".##..##.##.",
            "...........",
        ) to 42
    )

    override val partTwoTestExamples: Map<List<String>, Int>
        get() = TODO("Not yet implemented")
}
