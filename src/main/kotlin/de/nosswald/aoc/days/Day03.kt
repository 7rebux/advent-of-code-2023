package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/3
object Day03 : Day<Int>(3, "Gear Ratios") {
    private data class Point(val x: Int, val y: Int)

    override fun partOne(input: List<String>): Int {
        return combined(input, { it.isSymbol() }, List<Int>::sum)
    }

    override fun partTwo(input: List<String>): Int {
        return combined(input, { c -> c == '*' }, { nums ->
            if (nums.size == 2) nums.reduce(Int::times) else 0
        })
    }

    private fun combined(
        input: List<String>,
        isSymbolValid: (Char) -> Boolean,
        formula: (List<Int>) -> Int): Int
    {
        val checked = mutableSetOf<Point>()

        return input.mapIndexed { y, line ->
            line
                .withIndex()
                .filter { isSymbolValid(it.value) }
                .map { findAdjacentNumbers(input, Point(it.index, y), checked) }
                .sumOf { formula(it) }
        }.sum()
    }

    private fun Char.isSymbol(): Boolean {
        return !(this == '.' || this.isDigit())
    }

    private fun Point.getNeighbors(): List<Point> {
        val directions = listOf(-1, 0, 1)

        return directions
            .flatMap { dX -> directions.map { dY -> Point(x + dX, y + dY) } }
            .filter { it != this }
    }

    private fun findAdjacentNumbers(input: List<String>, point: Point, checked: MutableSet<Point>): List<Int> {
        return point.getNeighbors()
            .filter { p ->
                p.y in input.indices &&
                p.x in input[p.y].indices &&
                input[p.y][p.x].isDigit()
            }
            .mapNotNull { p ->
                if (checked.contains(p)) null else input[p.y].findNumberAt(p, checked)
            }
    }

    private fun String.findNumberAt(point: Point, checked: MutableSet<Point>): Int {
        val firstIndex = generateSequence(point.x, Int::dec)
            .takeWhile { it >= 0 && this[it].isDigit() }
            .last()
        val lastIndex = generateSequence(point.x, Int::inc)
            .takeWhile { it < this.length && this[it].isDigit() }
            .last()
        val indices = firstIndex..lastIndex

        // Prevent duplicate numbers
        checked.addAll(indices.map { Point(it, point.y) })

        return indices
            .map(this::get)
            .joinToString("")
            .toInt()
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..",
        ) to 4361
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..",
        ) to 467835
    )
}
