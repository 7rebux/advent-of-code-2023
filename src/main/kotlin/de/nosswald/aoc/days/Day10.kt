package de.nosswald.aoc.days

import de.nosswald.aoc.Day
import kotlin.math.ceil

// https://adventofcode.com/2023/day/10
object Day10 : Day<Int>(10, "Pipe Maze") {
    data class Point(val x: Int, val y: Int)

    override fun partOne(input: List<String>): Int {
        val start = Point(
            x = input.first { it.contains("S") }.indexOf("S"),
            y = input.indexOfFirst { it.contains("S") }
        )

        val seen = mutableSetOf(start)
        val queue = ArrayDeque<Point>()

        queue.addFirst(start)

        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeLast()
            val ch = input[r][c]

            if (r > 0 && ch in "S|JL" && input[r - 1][c] in "|7F" && !seen.contains(Point(r - 1, c))) {
                seen.add(Point(r - 1, c))
                queue.addFirst(Point(r - 1, c))
            }

            if (r < input.size - 1 && ch in "S|7F" && input[r + 1][c] in "|JL" && !seen.contains(Point(r + 1, c))) {
                seen.add(Point(r + 1, c))
                queue.addFirst(Point(r + 1, c))
            }

            if (c > 0 && ch in "S-J7" && input[r][c - 1] in "-LF" && !seen.contains(Point(r, c - 1))) {
                seen.add(Point(r, c - 1))
                queue.addFirst(Point(r, c - 1))
            }

            if (c < input[r].length - 1 && ch in "S-LF" && input[r][c + 1] in "-J7" && !seen.contains(Point(r, c + 1))) {
                seen.add(Point(r, c + 1))
                queue.addFirst(Point(r, c + 1))
            }
        }

        return ceil(seen.size / 2.0).toInt()
    }

    override fun partTwo(input: List<String>): Int {
        return 0
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            ".....",
            ".S-7.",
            ".|.|.",
            ".L-J.",
            ".....",
        ) to 4,

        listOf(
            "..F7.",
            ".FJ|.",
            "J.L7",
            "|F--J",
            "J...",
        ) to 8
    )

    override val partTwoTestExamples: Map<List<String>, Int>
        get() = TODO("Not yet implemented")
}
