package tasklist.stageThree
//
//import kotlinx.datetime.*
//
//enum class Priority(val string: String) {
//    C("C"), H("H"), N("N"), L("L")
//}
//
//class Task(
//    private val priority: Priority, private val date: String, private val time: String,
//    private val lines: List<String>
//) {
//    val isBlank get() = lines.isEmpty()
//
//    fun print(number: Int) {
//        val n = number.toString().padEnd(3)
//        println("$n$date $time $priority")
//
//        lines.forEach {
//            println("   $it") // 3 blanks in front
//        }
//    }
//}
//
//fun stageThree() {
//    val tasks = mutableListOf<Task>()
//
//    while (true) {
//        println("Input an action (add, print, end):")
//
//        when (readln().trim()) {
//            "end" -> {
//                println("Tasklist exiting!")
//                return
//            }
//            "add" -> add(tasks)
//            "print" -> print(tasks)
//            else -> println("The input action is invalid")
//        }
//    }
//}
//
//fun add(tasks: MutableList<Task>) {
//    val newTask = addNewTask()
//    if (newTask.isBlank) {
//        println("The task is blank")
//    } else {
//        tasks.add(newTask)
//    }
//}
//
//fun addNewTask(): Task {
//    val priority = getValidPriority()
//    val date = getValidDate()
//    val time = getValidTime()
//    val lines = getTheTasks()
//
//    return Task(priority, date, time, lines)
//}
//
//fun getValidPriority(): Priority {
//    while (true) {
//        val validPriority = Priority.values().map { it.string }
//        println("Input the task priority (${validPriority.joinToString(", ")}):")
//        val input = readln().uppercase()
//        if (input in validPriority) {
//            return Priority.valueOf(input)
//        }
//
//    }
//}
//
//fun getValidDate(): String {
//    while (true) {
//        println("Input the date (yyyy-mm-dd):")
//        try {
//            val datePart = readln().split('-').map { it.toInt() }
//            return LocalDate(datePart[0], datePart[1], datePart[2]).toString()
//        } catch (ex: Exception) {
//            println("The input date is invalid")
//        }
//    }
//}
//
//fun getValidTime(): String {
//    while (true) {
//        println("Input the time (hh:mm):")
//        try {
//            val timePart = readln().split(':').map { it.toInt() }
//            return LocalTime(timePart[0],timePart[1]).toString()
//        } catch (ex: Exception) {
//            println("The input time is invalid")
//        }
//    }
//}
//
//fun getTheTasks(): List<String> {
//    println("Input a new task (enter a blank line to end):")
//    val tasks = mutableListOf<String>()
//    while (true) {
//        val inputtedTask = readln().trim()
//        if (inputtedTask.isEmpty()) {
//            return tasks
//        } else {
//            tasks.add(inputtedTask)
//        }
//    }
//}
//
//fun print(tasks: List<Task>) {
//    if (tasks.isEmpty()) {
//        println("No tasks have been input")
//        return
//    }
//
//    tasks.forEachIndexed { i, task ->
//        task.print(i + 1)
//        println()
//    }
//}