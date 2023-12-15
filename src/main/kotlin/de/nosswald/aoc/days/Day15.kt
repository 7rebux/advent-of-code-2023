package de.nosswald.aoc.days

import de.nosswald.aoc.Day

// https://adventofcode.com/2023/day/15
object Day15 : Day<Int>(15, "Lens Library") {
    private fun appendix1A(str: String): Int {
        return str
            .map(Char::code)
            .fold(0) { acc, next -> (acc + next) * 17 % 256 }
    }

    override fun partOne(input: List<String>): Int {
        return input
            .first()
            .split(",")
            .sumOf(::appendix1A)
    }

    override fun partTwo(input: List<String>): Int {
        val boxes = mutableMapOf<Int, MutableList<String>>()
        val focalLengths = mutableMapOf<String, Int>()

        input.first().split(",").forEach { instruction ->
            if (instruction.contains('-')) {
                val label = instruction.split("-").first()
                val index = appendix1A(label)

                boxes[index]?.remove(label)
            } else {
                val (label, focalLength) = instruction.split("=")
                val index = appendix1A(label)
                val box = boxes.getOrPut(index) { mutableListOf() }

                if (!box.contains(label)) box.add(label)
                focalLengths[label] = focalLength.toInt()
            }
        }

        return boxes.toList().sumOf { (box, values) ->
            values.withIndex().sumOf { (slot, label) ->
                (box + 1) * (slot + 1) * focalLengths[label]!!
            }
        }
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7") to 1320
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7") to 145
    )
}
