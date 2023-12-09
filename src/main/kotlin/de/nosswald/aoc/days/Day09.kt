package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/8
object Day09 : Day<Int>(9, "Mirage Maintenance") {
    private fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { line -> line.split(" ").map(String::toInt) }
    }

    private fun nextInSequence(differences: List<Int>): Int {
        if (differences.sum() == 0) {
            return 0
        }

        val next = differences.zipWithNext().map { it.second - it.first }

        return differences.last() + nextInSequence(next)
    }

    override fun partOne(input: List<String>): Int {
        return parseInput(input).sumOf(::nextInSequence)
    }

    override fun partTwo(input: List<String>): Int {
        return parseInput(input).map(List<Int>::reversed).sumOf(::nextInSequence)
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45",
        ) to 114
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45",
        ) to 2
    )
}
