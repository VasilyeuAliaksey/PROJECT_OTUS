package data

import org.jetbrains.exposed.dao.id.IntIdTable

object TasksForTest: IntIdTable("test_data") {
    val name = varchar("name", 100)
    val priority = varchar("priority", 10)
}