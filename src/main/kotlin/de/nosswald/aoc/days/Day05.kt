package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/5
object Day05 : Day<Long>(5, "If You Give A Seed A Fertilizer") {
    data class RangeEntry(val destinationStart: Long, val sourceStart: Long, val length: Long) {
        val sourceRange get(): LongRange {
            return sourceStart until sourceStart + length
        }

        fun destinationForSource(source: Long): Long {
            return destinationStart + (source - sourceStart)
        }
    }

    data class ConversionMap(val entries: List<RangeEntry>)

    private fun parseInput(input: List<String>): Pair<List<Long>, List<ConversionMap>> {
        val seeds = input
            .first()
            .removePrefix("seeds: ")
            .split(" ")
            .map(String::toLong)
        val maps = input
            .drop(2)
            .joinToString("\n")
            .split("\n\n")
            .map { block ->
                block
                    .split("\n")
                    .drop(1)
                    .map { line ->
                        line
                            .split(" ")
                            .map(String::toLong)
                    }
                    .map {
                        RangeEntry(it[0], it[1], it[2])
                    }
            }
            .map {
                ConversionMap(it)
            }

        return Pair(seeds, maps)
    }

    override fun partOne(input: List<String>): Long {
        val (seeds, maps) = parseInput(input)

        return maps.fold(seeds) { acc, map ->
            acc.map { source ->
                map.entries.find { source in it.sourceRange }?.destinationForSource(source) ?: source
            }
        }.min()
    }

    override fun partTwo(input: List<String>): Long {
        val (seeds, maps) = parseInput(input)
        val seedRanges = seeds
            .windowed(2, 2)
            .map { it[0] .. it[0] + it[1] }

        val reversedMaps = maps.map {
            ConversionMap(it.entries.map { old ->
                RangeEntry(old.sourceStart, old.destinationStart, old.length)
            })
        }.reversed()

        return generateSequence(0, Long::inc).first { location ->
            val seed = reversedMaps.fold(location) { acc, map ->
                map.entries.find { acc in it.sourceRange }?.destinationForSource(acc) ?: acc
            }

            seedRanges.any { seedRange -> seed in seedRange }
        }
    }

    private val exampleInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent().split("\n").toList()

    override val partOneTestExamples: Map<List<String>, Long> = mapOf(
        exampleInput to 35
    )

    override val partTwoTestExamples: Map<List<String>, Long> = mapOf(
        exampleInput to 46
    )
}
