package tasklist.stageOne


fun stageOne() {
    val userTasks = readTasks()

    printUserTasks(userTasks)
}

fun readTasks(): List<String> {
    println("Input the tasks (enter a blank line to end):")

    val tasks = mutableListOf<String>()
    while (true) {
        val task = readln().trim()
        if (task.isEmpty()) {
            break
        } else {
            tasks.add(task)
        }
    }
    return tasks
}

fun printUserTasks(tasks: List<String>) {
    if (tasks.isEmpty()) {
        println("No tasks have been input.")
        return
    }

    tasks.forEachIndexed { i, task ->
        val index = (i + 1).toString().padEnd(2, ' ')
        println("$index $task")
    }
}