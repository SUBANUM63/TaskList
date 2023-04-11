package tasklist.stageFour

import kotlinx.datetime.*

enum class Priority(val string: String) {
    C("C"), H("H"), N("N"), L("L")
}

// we store the date and time as an ISO String (localDateTime.toString())
class Task(
    val priority: Priority,
    val isoDateTime: String,
    val lines: List<String>
) {
    val isBlank = lines.isEmpty()

    fun print(number: Int) {
        val n = number.toString().padEnd(3)
        val dateAndTime = isoDateTime.replace('T', ' ')
        println("$n$dateAndTime $priority ${getDueTag()}")

        lines.forEach {
            println("   $it") // 3 blanks in front
        }
    }

    private fun getDueTag(): String {
        val taskDate = LocalDateTime.parse(isoDateTime)
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val numberOfDays = currentDate.daysUntil(taskDate.date)

        return when {
            numberOfDays == 0 -> "T"
            numberOfDays > 0 -> "I"
            else -> "O"
        }
    }
}

fun stageFour() {
    val tasks = mutableListOf<Task>()

    while (true) {
        println("Input an action (add, print, edit, delete, end):")

        when (readln().trim()) {
            "end" -> {
                println("Tasklist exiting!")
                return
            }
            "add" -> addAction(tasks)
            "print" -> printAction(tasks)
            "edit" -> editAction(tasks)
            "delete" -> deleteAction(tasks)
            else -> println("The input action is invalid")
        }
    }
}

fun addAction(tasks: MutableList<Task>) {
    val newTask = getNewTask()
    if (newTask.isBlank) {
        println("The task is blank")
    } else {
        tasks.add(newTask)
    }
}

fun editAction(tasks: MutableList<Task> ) {
    printAction(tasks)

    if (tasks.isEmpty()) return

    val index = getValidIndexOfTask(tasks.size)
    val oldTask = tasks[index]

    when (getValidTaskField()) {
        "priority" -> {
            val newPriority = getValidPriority()
            tasks[index] = Task(newPriority, oldTask.isoDateTime, oldTask.lines)
        }
        "date" -> {
            val newLocalDate = getValidLocalDate()
            val old = oldTask.isoDateTime.toLocalDateTime()
            val newLocalDateTime = LocalDateTime(newLocalDate.year, newLocalDate.month, newLocalDate.dayOfMonth, old.hour, old.minute)
            tasks[index] = Task(oldTask.priority, newLocalDateTime.toString(), oldTask.lines)
        }
        "time" -> {
            val newLocalDateTime = getValidLocalDateTime(oldTask.isoDateTime.toLocalDateTime().date)
            tasks[index] = Task(oldTask.priority, newLocalDateTime.toString(), oldTask.lines)
        }
        "task" -> {
            val newLines = getTaskLines()
            tasks[index] = Task(oldTask.priority, oldTask.isoDateTime, newLines)
        }
    }
    println("The task is changed")
}

fun getValidTaskField(): String {
    while (true) {
        val fields = listOf("priority", "date", "time", "task")
        println("Input a field to edit (${fields.joinToString(", ")}):")
        val input = readln()
        if (input in fields) {
            return input
        } else {
            println("Invalid field")
        }
    }
}

fun deleteAction(tasks: MutableList<Task>) {
    printAction(tasks)

    if (tasks.isEmpty()) return

    val index = getValidIndexOfTask(tasks.size)
    tasks.removeAt(index)
    println("The task is deleted")
}

fun getValidIndexOfTask(noOfTasks: Int): Int {
    while (true) {
        println("Input the task number (1-$noOfTasks):")
        try {
            val input = readln().toInt()
            if (input in 1..noOfTasks) {
                return input - 1
            } else {
                println("Invalid task number")
            }
        } catch (nfex: NumberFormatException) {
            println("Invalid task number")
        }
    }
}

fun getNewTask(): Task {
    val priority = getValidPriority()
    val localDate = getValidLocalDate()
    val localDateTime = getValidLocalDateTime(localDate)
    val lines = getTaskLines()

    return Task(priority, localDateTime.toString(), lines)
}

fun getValidPriority():Priority {
    while (true) {
        val validPrios = Priority.values().map { it.string }
        println("Input the task priority (${validPrios.joinToString(", ")}):")
        val input = readln().uppercase()
        if (input in validPrios) {
            return Priority.valueOf(input)
        }
    }
}

fun getValidLocalDate(): LocalDate {
    while (true) {
        println("Input the date (yyyy-mm-dd):")
        try {
            return parseDate(readln())
        } catch (ex: Exception) {
            println("The input date is invalid")
        }
    }
}

// parse yyyy-mm-dd to LocalDate
// attention: user can only enter one month or day (no leading 0s), e.g. 2022-1-3
fun parseDate(s: String): LocalDate {
    val parts = s.split('-').map { it.toInt() }
    return LocalDate(parts[0], parts[1], parts[2])
}

// user inputs time, and we add this to the given LocalDate
fun getValidLocalDateTime(localDate: LocalDate): LocalDateTime {
    while (true) {
        println("Input the time (hh:mm):")
        try {
            val parts = readln().split(':').map { it.toInt() }
            return LocalDateTime(localDate.year, localDate.month, localDate.dayOfMonth, parts[0], parts[1])
        } catch (ex: Exception) {
            println("The input time is invalid")
        }
    }
}

fun getTaskLines(): List<String> {
    println("Input a new task (enter a blank line to end):")
    val lines = mutableListOf<String>()
    while (true) {
        val input = readln().trim()
        if (input.isEmpty()) {
            return lines
        } else {
            lines.add(input)
        }
    }
}

fun printAction(tasks: List<Task>) {
    if (tasks.isEmpty()) {
        println("No tasks have been input")
        return
    }

    printTasks(tasks)
}

fun printTasks(tasks: List<Task>) {
    tasks.forEachIndexed { i, task ->
        task.print(i + 1)
        println()
    }
}