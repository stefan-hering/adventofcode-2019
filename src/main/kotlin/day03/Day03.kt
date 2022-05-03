package day03

import java.lang.Math.abs

val input = Unit::javaClass.get().getResource("/day03/input")
    .readText()
    .lines()
    .filter { it.isNotBlank() }
    .map { list ->
        list.split(",")
            .filter { it.isNotBlank() }
            .map {
                Instruction(it.first(), it.substring(1).toInt())
            }
    }

data class Instruction(val direction: Char, val value: Int)

data class Position(val x: Int = 0, val y: Int = 0) {
    fun move(instruction: Instruction): Position =
        when (instruction.direction) {
            'U' -> copy(y = y + instruction.value)
            'D' -> copy(y = y - instruction.value)
            'R' -> copy(x = x + instruction.value)
            'L' -> copy(x = x - instruction.value)
            else -> throw Exception("kapott")
        }
}

typealias Line = Pair<Position, Position>

fun Line.isHorizontal() = first.x != second.x

fun Line.length() = abs(first.x - second.x + first.y - second.y)

fun Line.intersection(other: Line): Position? {
    if (isHorizontal() == other.isHorizontal()) {
        return null
    }
    val (horizontal, vertical) = if (isHorizontal()) this to other else other to this

    return if (minOf(horizontal.first.x, horizontal.second.x) < vertical.first.x &&
        maxOf(horizontal.first.x, horizontal.second.x) > vertical.first.x &&
        minOf(vertical.first.y, vertical.second.y) < horizontal.first.y &&
        maxOf(vertical.first.y, vertical.second.y) > horizontal.first.y
    ) {
        Position(vertical.first.x, horizontal.first.y)
    } else {
        null
    }
}

fun getWires(instructions: List<List<Instruction>>) =
    instructions.map {
        it.runningFold(Position()) { position, instruction ->
            position.move(instruction)
        }.zipWithNext()
    }

fun part1(wires: List<List<Line>>): Int {
    var minDistance = Int.MAX_VALUE
    for (line1 in wires[0]) {
        for (line2 in wires[1]) {
            line1.intersection(line2)?.let {
                minDistance = minOf(minDistance,abs(it.x) + abs(it.y))
            }
        }
    }

    return minDistance
}

fun part2(wires: List<List<Line>>): Int {
    var minDistance = Int.MAX_VALUE
    var currentDistance = 0
    for(line1 in wires[0]) {
        currentDistance += line1.length()
        var fullDistance = currentDistance

        for(line2 in wires[1]) {
            fullDistance += line2.length()

            line2.intersection(line1)?.let {
                var distance = fullDistance

                distance -= (line2.second to it).length()
                distance -= (line1.second to it).length()

                minDistance = minOf(minDistance, distance)
            }
        }
    }

    return minDistance
}

fun main() {
    val wires = getWires(input)
    println(part1(wires))
    println(part2(wires))
}
