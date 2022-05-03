package day04

val input = 123257 to 647015

fun part1(): Int {
    var counter = 0
    for (i in input.first..input.second) {
        val chars = i.toString().toCharArray()

        if (chars.asSequence().zipWithNext().none { (a, b) -> b < a } &&
            chars.asSequence().zipWithNext().any { (a, b) -> a == b }
        ) {
            counter++
        }
    }

    return counter
}

fun part2(): Int {
    var counter = 0
    for (i in input.first..input.second) {
        val chars = i.toString().toCharArray()

        if (chars.asSequence().zipWithNext().none { (a, b) -> b < a } &&
            chars.groupBy { it }.values.any { it.size == 2 }
        ) {
            counter++
        }
    }

    return counter
}

fun main() {
    println(part1())
    println(part2())
}
