package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/1
object Day01 : Day<Int>(1, "Trebuchet?!") {
    private val digitsMap = mapOf(
        "one"   to 1,
        "two"   to 2,
        "three" to 3,
        "four"  to 4,
        "five"  to 5,
        "six"   to 6,
        "seven" to 7,
        "eight" to 8,
        "nine"  to 9,
    )

    override fun partOne(input: List<String>): Int {
        return input.sumOf { line ->
            val first = line.first(Char::isDigit)
            val last = line.last(Char::isDigit)

            return@sumOf "$first$last".toInt()
        }
    }

    override fun partTwo(input: List<String>): Int {
        return input.sumOf { line ->
            val digits = mutableListOf<Int>()
            var temp = line

            while (temp.isNotEmpty()) {
                if (temp.first().isDigit()) {
                    digits += temp.first().digitToInt()
                } else {
                    digitsMap.forEach { (k, v) ->
                        if (temp.startsWith(k)) {
                            digits += v
                            return@forEach
                        }
                    }
                }

                temp = temp.drop(1)
            }

            return@sumOf digits.first() * 10 + digits.last()
        }
    }

    @Suppress("spellCheckingInspection")
    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet"
        ) to 142,
    )

    @Suppress("spellCheckingInspection")
    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen"
        ) to 281
    )
}
