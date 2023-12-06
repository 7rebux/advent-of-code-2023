package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/6
object Day06 : Day<Int>(6, "Wait For It") {
    override fun partOne(input: List<String>): Int {
        val (times, records) = input.map { line ->
            line
                .split(":")
                .last()
                .split(" ")
                .filter(String::isNotBlank)
                .map(String::toLong)
        }

        return times.zip(records).map { (time, record) -> countPossibleRecords(time, record) }.reduce(Int::times)
    }

    override fun partTwo(input: List<String>): Int {
        val (time, record) = input.map { line ->
            line
                .split(":")
                .last()
                .filterNot(Char::isWhitespace)
                .toLong()
        }

        return countPossibleRecords(time, record)
    }

    private fun countPossibleRecords(time: Long, record: Long): Int {
        return (1..time).count { (time - it) * it > record }
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200",
        ) to 288
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200",
        ) to 71503
    )
}
