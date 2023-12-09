import de.nosswald.aoc.Day
import de.nosswald.aoc.days.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DaysTest {
    data class Answer<T>(
        val day: Day<T>,
        val partOne: T,
        val partTwo: T
    )

    @TestFactory
    fun answers() = listOf(
        Answer(Day01, 56042, 55358),
        Answer(Day02, 2913, 55593),
        Answer(Day03, 522726, 81721933),
        Answer(Day04, 21485, 11024379),
        Answer(Day05, 251346198, 72263011),
        Answer(Day06, 1083852, 23501589),
        Answer(Day07, 250898830, 252127335),
        Answer(Day08, 18827, 20220305520997),
        Answer(Day09, 1647269739, 864),
    ).map {
        DynamicTest.dynamicTest("Day ${it.day.number} - ${it.day.title}") {
            if (it.day.partOneTestExamples.isNotEmpty()) {
                print("Testing Part 1 examples..")
                it.day.partOneTestExamples.entries.forEach { entry ->
                    Assertions.assertEquals(entry.value, it.day.partOne(entry.key))
                }
                print(" SUCCESS\n")
            }

            print("Testing Part 1..")
            Assertions.assertEquals(it.partOne, it.day.partOne())
            print(" SUCCESS\n")

            if (it.day.partTwoTestExamples.isNotEmpty()) {
                print("Testing Part 2 examples..")
                it.day.partTwoTestExamples.entries.forEach { entry ->
                    Assertions.assertEquals(entry.value, it.day.partTwo(entry.key))
                }
                print(" SUCCESS\n")
            }

            print("Testing Part 2..")
            Assertions.assertEquals(it.partTwo, it.day.partTwo())
            print(" SUCCESS\n")
        }
    }
}
