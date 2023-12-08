package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/8
object Day08 : Day<Long>(8, "Haunted Wasteland") {
    private enum class Direction { LEFT, RIGHT }

    private fun parseInput(input: List<String>): Pair<List<Direction>, Map<String, Pair<String, String>>> {
        val directions = input.first().map { if (it == 'L') Direction.LEFT else Direction.RIGHT }
        val entries = input.drop(2).map { line ->
            val instruction = line.split(" = ")
            val (left, right) = instruction
                .last()
                .split("(", ")", ", ")
                .filter(String::isNotBlank)

            return@map instruction.first() to Pair(left, right)
        }.toMap()

        return Pair(directions, entries)
    }

    private fun countSteps(
        from: String,
        until: (String) -> Boolean,
        network: Pair<List<Direction>, Map<String, Pair<String, String>>>
    ): Int {
        val (directions, entries) = network
        var current = from
        var steps = 0

        while (!until(current)) {
            val next = entries[current]!!
            val dir = directions[steps % directions.size]

            current = if (dir == Direction.LEFT) next.first else next.second
            steps++
        }

        return steps
    }

    override fun partOne(input: List<String>): Long {
        return countSteps(
            from = "AAA",
            until = { it == "ZZZ" },
            parseInput(input)
        ).toLong()
    }

    override fun partTwo(input: List<String>): Long {
        val network = parseInput(input)

        return network.second
            .filter { it.key.last() == 'A' }
            .map { entry -> countSteps(entry.key, { it.last() == 'Z' }, network) }
            .map(Int::toBigInteger)
            .reduce { acc, steps -> acc * steps / acc.gcd(steps) }
            .toLong()
    }

    override val partOneTestExamples: Map<List<String>, Long> = mapOf(
        listOf(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)",
        ) to 2,

        listOf(
            "LLR",
            "",
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)",
        ) to 6
    )

    override val partTwoTestExamples: Map<List<String>, Long> = mapOf(
        listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)",
        ) to 6
    )
}
