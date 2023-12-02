package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/2
object Day02 : Day<Int>(2, "Cube Conundrum") {
    private data class Game(val id: Int, val sets: List<Set>)
    private data class Set(val cubes: MutableMap<Color, Int>)
    private enum class Color { RED, GREEN, BLUE }

    private fun parseGames(input: List<String>): List<Game> {
        return input.mapIndexed { index, line ->
            val sets = buildList {
                line.split(": ")[1].split("; ").forEach { set ->
                    val current = Set(mutableMapOf())

                    set.split(", ").forEach {
                        val (amount, color) = it.split(" ")

                        when (color) {
                            "red" -> current.cubes[Color.RED] = amount.toInt()
                            "green" -> current.cubes[Color.GREEN] = amount.toInt()
                            "blue" -> current.cubes[Color.BLUE] = amount.toInt()
                            else -> error("Invalid color: $color")
                        }
                    }

                    this.add(current)
                }
            }

            return@mapIndexed Game(index + 1, sets)
        }
    }

    override fun partOne(input: List<String>): Int {
        val maxCubesByColor = mapOf(
            Color.RED   to 12,
            Color.GREEN to 13,
            Color.BLUE  to 14,
        )

        return parseGames(input).sumOf { game ->
            game.sets.forEach { set ->
                if (set.cubes.any { (color, amount) -> amount > maxCubesByColor[color]!! })
                    return@sumOf 0
            }

            return@sumOf game.id
        }
    }

    override fun partTwo(input: List<String>): Int {
        return parseGames(input).sumOf { game ->
            val minCubesNeededByColor = mutableMapOf(
                Color.RED   to 0,
                Color.GREEN to 0,
                Color.BLUE  to 0,
            )

            game.sets.forEach { set ->
                set.cubes.forEach { (color, amount) ->
                    minCubesNeededByColor[color] = maxOf(minCubesNeededByColor[color]!!, amount)
                }
            }

            return@sumOf minCubesNeededByColor.values.reduce(Int::times)
        }
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        ) to 8
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        ) to 2286
    )
}
