package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/7
object Day07 : Day<Int>(7, "Camel Cards") {
    private data class Hand(val cards: String, val bidAmount: Int): Comparable<Hand> {
        val groups = cards.groupBy { it }.map { it.value.size }.sortedDescending()

        override fun compareTo(other: Hand): Int {
            return compareBy( { it.groups[0] }, { it.groups[1] }, Hand::cards).compare(this, other)
        }
    }

    private fun parseInput(input: List<String>): List<Hand> {
        return input.map { line ->
            val (cards, bidAmount) = line.split(" ")
            val betterCards = cards
                .replace('A', Char('9'.code + 5))
                .replace('K', Char('9'.code + 4))
                .replace('Q', Char('9'.code + 3))
                .replace('J', Char('9'.code + 2))
                .replace('T', Char('9'.code + 1))

            return@map Hand(betterCards, bidAmount.toInt())
        }
    }

    override fun partOne(input: List<String>): Int {
        val hands = parseInput(input)

        return hands
            .sorted()
            .mapIndexed { i, (_, bidAmount) -> (i + 1) * bidAmount }
            .sum()
    }

    override fun partTwo(input: List<String>): Int {
        return 0
    }

    @Suppress("spellCheckingInspection")
    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        ) to 6440
    )

    @Suppress("spellCheckingInspection")
    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        ) to 5905
    )
}
