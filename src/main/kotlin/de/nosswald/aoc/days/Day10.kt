package de.nosswald.aoc.days

import de.nosswald.aoc.Day
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

// https://adventofcode.com/2023/day/10
object Day10 : Day<Int>(10, "Pipe Maze") {
    data class Point(val x: Int, val y: Int)

    override fun partOne(input: List<String>): Int {
        return ceil(findLoopPoints(input).size / 2.0).toInt()
    }

    override fun partTwo(input: List<String>): Int {
        val maze = input.flatMapIndexed { y: Int, line: String -> line.mapIndexed { x, _ -> Point(x, y) } }
        val loop = findLoopPoints(input).toList()

        return maze.filterNot(loop::contains).count { isPointInside(it, loop) }
    }

    private fun findLoopPoints(input: List<String>): Set<Point> {
        val start = Point(
            x = input.first { it.contains("S") }.indexOf("S"),
            y = input.indexOfFirst { it.contains("S") }
        )

        val path = mutableSetOf(start)

        while (true) {
            val (x, y) = path.last()
            val ch = input[y][x]

            // Up
            if (ch in "S|JL" && input[y - 1][x] in "|7F" && !path.contains(Point(x, y - 1))) {
                path.add(Point(x, y - 1))
            }
            // Down
            else if (ch in "S|7F" && input[y + 1][x] in "|JL" && !path.contains(Point(x, y + 1))) {
                path.add(Point(x, y + 1))
            }
            // Left
            else if (ch in "S-J7" && input[y][x - 1] in "-LF" && !path.contains(Point(x - 1, y))) {
                path.add(Point(x - 1, y))
            }
            // Right
            else if (ch in "S-LF" && input[y][x + 1] in "-J7" && !path.contains(Point(x + 1, y))) {
                path.add(Point(x + 1, y))
            }
            // Reached start point
            else return path
        }
    }

    private fun isPointInside(point: Point, loop: List<Point>): Boolean {
        var inside = false
        var p1 = loop.first()

        for (i in 1..loop.size) {
            val p2 = loop[i % loop.size]

            if (point.y > min(p1.y, p2.y)) {
                if (point.y <= max(p1.y, p2.y)) {
                    if (point.x <= max(p1.x, p2.x)) {
                        val intersection = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x

                        if (p1.x == p2.x || point.x <= intersection) {
                            inside = !inside
                        }
                    }
                }
            }

            p1 = p2
        }

        return inside
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
            "SJ.L7",
            "|F--J",
            "LJ...",
        ) to 8
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "...........",
            ".S-------7.",
            ".|F-----7|.",
            ".||.....||.",
            ".||.....||.",
            ".|L-7.F-J|.",
            ".|..|.|..|.",
            ".L--J.L--J.",
            "...........",
        ) to 4,

        listOf(
            ".F----7F7F7F7F-7....",
            ".|F--7||||||||FJ....",
            ".||.FJ||||||||L7....",
            "FJL7L7LJLJ||LJ.L-7..",
            "L--J.L7...LJS7F-7L7.",
            "....F-J..F7FJ|L7L7L7",
            "....L7.F7||L7|.L7L7|",
            ".....|FJLJ|FJ|F7|.LJ",
            "....FJL-7.||.||||...",
            "....L---J.LJ.LJLJ...",
        ) to 8
    )
}
