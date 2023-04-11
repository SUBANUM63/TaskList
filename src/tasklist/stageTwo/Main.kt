package tasklist.stageTwo
class Task(private var lines: List<String>) {
    val isBlank get() = lines.isEmpty()

    fun print(number: Int) {
        val n = number.toString().padEnd(3)
        println("$n${lines.first()}")

        lines.drop(1).forEach {
            println("   $it") // 3 blanks in front
        }
    }
}

fun stageTwo() {
    val tasks = mutableListOf<Task>()

    while (true) {
        println("Input an action (add, print, end):")

        when (readln().trim()) {
            "end" -> {
                println("Tasklist exiting!")
                return
            }
            "add" -> fetchNewTask().let { if (it.isBlank) println("The task is blank") else tasks.add(it) }
            "print" -> printAllTasks(tasks)
            else -> println("The input action is invalid")
        }
    }
}

fun fetchNewTask(): Task {
    println("Input a new task (enter a blank line to end):")

    val lines = mutableListOf<String>()
    while (true) {
        val newTask = readln().trim()
        if (newTask.isEmpty()) {
            break
        } else {
            lines.add(newTask)
        }
    }

    return Task(lines)
}

fun printAllTasks(tasks: List<Task>) {
    if (tasks.isEmpty()) {
        println("No tasks have been input")
        return
    }

    tasks.forEachIndexed { i, task ->
        task.print(i + 1)
        println()
    }
}

