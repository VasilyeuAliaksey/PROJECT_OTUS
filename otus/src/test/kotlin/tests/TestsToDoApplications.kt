package tests

import data.Task
import data.TasksRepositoryMemory
import helpers.DataRepository
import io.qameta.allure.Allure.step
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.streams.toList
import kotlin.test.assertContains
import kotlin.test.assertFalse


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestsToDoApplications {
    private val dataTestRepo = DataRepository()

    private fun getTestTasks(): Stream<Task> {
        return dataTestRepo.testTasks.stream()
    }

    @ParameterizedTest
    @MethodSource("getTestTasks")
    fun addTest(task: Task) {
        val repository = TasksRepositoryMemory()
        var addedTask: Task = task
        step {
            it.name("Add a task to the repository")
            val taskId = repository.addTask(task)
            addedTask = task.copy(id = taskId)
        }
        step {
            it.name("Check if added task is in the repository")
            assertContains(repository.tasks, addedTask, "The task in not in the repository")
        }
    }

    @Test
    fun deleteTest() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = dataTestRepo.testTasks
        val idOfDeletedTask: Int = Random.nextInt(1, tasks.size)
        val indexOfDeletedTask = idOfDeletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the TasksRepository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Delete a task")
            repository.deleteTask(idOfDeletedTask)
        }
        step {
            it.name("Check if deleted task is not in the repository")
            assertFalse {
                repository.getTasks(false).contains(tasksAfterAdding[indexOfDeletedTask])
            }
        }
    }

    @Test
    fun uncompleteTest() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = dataTestRepo.testTasks
        val idOfCompletedTask: Int = Random.nextInt(1, tasks.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the repository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Complete a random task")
            repository.completeTask(idOfCompletedTask)
        }
        step {
            it.name("Uncomplete the task")
            repository.uncompleteTask(idOfCompletedTask)
        }
        step {
            it.name("Check if the task is uncompleted")
            assertContains(repository.getTasks(true), tasksAfterAdding[indexOfCompletedTask])
        }
    }


    @Test
    fun filterIncludingTest() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = getTestTasks().toList()
        val idOfCompletedTask = Random.nextInt(1, tasks.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the repository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Complete a random task")
            repository.completeTask(idOfCompletedTask)
            tasksAfterAdding[indexOfCompletedTask].completed = true
        }
        step {
            it.name("Check filter of tasks including completed")
            assertContains(repository.getTasks(true), tasksAfterAdding[indexOfCompletedTask], "The task in not in the repository")
        }
    }

    @Test
    fun filterExcludingTest() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = dataTestRepo.testTasks
        val idOfCompletedTask: Int = Random.nextInt(1, tasks.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the TasksRepository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Complete a task")
            repository.completeTask(idOfCompletedTask)
            tasksAfterAdding[indexOfCompletedTask].completed = true
        }
        step {
            it.name("Check filter of tasks excluding completed")
            assertFalse {
                repository.getTasks(false).contains(tasksAfterAdding[indexOfCompletedTask])
            }
        }
    }

}