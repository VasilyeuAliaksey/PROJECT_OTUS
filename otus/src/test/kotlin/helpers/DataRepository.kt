package helpers

import data.DatabaseManager

class DataRepository {
    private val numberOfTasks = 10
    val testTasks = DatabaseManager().getTaskList(numberOfTasks)
}