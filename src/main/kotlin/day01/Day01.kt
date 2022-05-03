package day01

val input = Unit::javaClass.get().getResource("/day01/input")
    .readText()
    .lines()
    .filter { it.isNotBlank() }
    .map { it.toInt() }

fun part1(modules: List<Int>): Int =
    modules.asSequence()
        .map(::costOfModule)
        .sum()

fun costOfModule(weight: Int) = weight / 3 - 2

fun part2(modules: List<Int>): Int =
    modules.asSequence()
        .map {
            var fuel = costOfModule(it)
            var totalFuel = 0
            while (fuel > 0) {
                totalFuel += fuel
                fuel = costOfModule(fuel)
            }
            totalFuel
        }
        .sum()

fun main() {
    println(part1(input))
    println(part2(input))
}
