package de.nosswald.aoc.days

import de.nosswald.aoc.Day
import kotlin.math.pow

// https://adventofcode.com/2023/day/4
object Day04 : Day<Int>(4, "Scratchcards") {
    private data class Card(val id: Int, val winning: List<Int>, val actual: List<Int>)

    private fun parseInput(input: List<String>): List<Card> {
        return input.mapIndexed { index, line ->
            val (winning, actual) = line
                .substringAfter(':')
                .split("|")
                .map { numbers ->
                    numbers
                        .split(" ")
                        .filter(String::isNotBlank)
                        .map(String::toInt)
                }

            return@mapIndexed Card(index + 1, winning, actual)
        }
    }

    override fun partOne(input: List<String>): Int {
        return parseInput(input).sumOf { card ->
            // 2 to the power of -1 is 0.5, so after converting it to an integer its 0
            2.toDouble().pow(card.actual.count(card.winning::contains) - 1).toInt()
        }
    }

    override fun partTwo(input: List<String>): Int {
        val cards = parseInput(input)
        val copies = cards
            .associate { it.id to 1 }
            .toMutableMap()

        cards.forEach { card ->
            val matching = card.actual.count(card.winning::contains)
            val cardsToCopy = (card.id + 1)..(card.id + matching)

            cardsToCopy.forEach { id ->
                copies[id] = copies[id]!! + copies[card.id]!!
            }
        }

        return copies.values.sum()
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
        ) to 13
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
        ) to 30
    )
}
