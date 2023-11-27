package data

import helpers.getRandomString
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConnection {
    val connect = Database.connect("jdbc:sqlite:test_data.db", "org.sqlite.JDBC")
}

class DatabaseManager {
    private fun testDataGenerate(numberOfTasks: Int) {
        DatabaseConnection.connect
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(TasksForTest)
            for (i in 0..numberOfTasks) {
                TasksForTest.insert {
                    it[name] = getRandomString((0..100).random())
                    it[priority] = Priority.entries.random().name
                }
            }
        }
    }
    private fun clear() {
        TasksForTest.deleteAll()
    }

    fun getTaskList(numberOfTasks: Int): MutableList<Task> {
        testDataGenerate(numberOfTasks)
        val tasks = mutableListOf<Task>()
        transaction {
            addLogger(StdOutSqlLogger)
            TasksForTest.selectAll().forEach {
                val name = it[TasksForTest.name]
                val priority = Priority.valueOf(it[TasksForTest.priority])
                tasks.add(Task(name = name, priority = priority))
            }
            clear()
        }
        return tasks
    }
}