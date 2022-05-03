package day02

val input = Unit::javaClass.get().getResource("/day02/input")
    .readText()
    .lines()
    .first { it.isNotBlank() }
    .split(",")
    .map { it.toInt() }

fun part1(initialProgram: List<Int>): Int {
    val instructions = initialProgram.toMutableList()

    instructions[1] = 12
    instructions[2] = 2

    return runProgram(instructions)
}

fun runStep(i: Int, instructions: MutableList<Int>): Boolean {
    val n1 = instructions[i+1]
    val n2 = instructions[i+2]
    val target = instructions[i+3]
    when(instructions[i]) {
        1 -> instructions[target] = instructions[n1] + instructions[n2]
        2 -> instructions[target] = instructions[n1] * instructions[n2]
        99 -> return false
        else -> TODO()
    }

    return true
}

fun runProgram(program: MutableList<Int>): Int {
    var i = 0
    while(runStep(i, program)) {
        i += 4
    }

    return program[0]
}

fun part2(initialProgram: List<Int>): Int {
    for(a in 0..99) {
        for(b in 0..99) {
            val program = initialProgram.toMutableList()
            program[1] = a
            program[2] = b
            if(runProgram(program) == 19690720) {
                return 100 * a + b
            }
        }
    }
    return 0
}

fun main() {
    println(part1(input))
    println(part2(input))
}
