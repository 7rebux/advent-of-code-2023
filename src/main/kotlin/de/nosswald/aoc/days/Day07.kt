package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/7
object Day07 : Day<Int>(7, "Camel Cards") {
    private const val JOKER_PART_TWO = '1'

    private data class Hand(val cards: String, val bidAmount: Int): Comparable<Hand> {
        var groups = cards
            .groupBy { it }
            .map { it.key to it.value.size }
            .sortedByDescending { it.second }
            .toMutableList()

        fun handleJokerCards() {
            cards.count { it == JOKER_PART_TWO }.let { jokerCards ->
                if (jokerCards in 1..4) {
                    groups.removeIf { it.first == JOKER_PART_TWO }
                    groups[0] = groups[0].first to groups[0].second + jokerCards
                }
            }
        }

        override fun compareTo(other: Hand): Int {
            return compareBy(
                { it.groups[0].second },
                { it.groups.getOrNull(1)?.second },
                Hand::cards
            ).compare(this, other)
        }
    }

    private fun parseInput(input: List<String>, partTwo: Boolean = false): List<Hand> {
        return input.map { line ->
            val (cards, bidAmount) = line.split(" ")
            val betterCards = cards
                .replace('A', Char('9'.code + 5))
                .replace('K', Char('9'.code + 4))
                .replace('Q', Char('9'.code + 3))
                .replace('J', if (partTwo) JOKER_PART_TWO else Char('9'.code + 2))
                .replace('T', Char('9'.code + 1))

            return@map Hand(betterCards, bidAmount.toInt())
        }
    }

    private fun calculateWinnings(hands: List<Hand>): Int {
        return hands
            .sorted()
            .mapIndexed { i, (_, bidAmount) -> (i + 1) * bidAmount }
            .sum()
    }

    override fun partOne(input: List<String>): Int = calculateWinnings(parseInput(input))

    override fun partTwo(input: List<String>): Int {
        return parseInput(input, partTwo = true)
            .onEach(Hand::handleJokerCards)
            .run(::calculateWinnings)
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
