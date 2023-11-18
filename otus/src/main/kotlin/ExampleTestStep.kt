class ExampleTestStep {
    fun beforeOne() {
        println("Выполнение метода")
    }

    fun beforeTestTwo() {
        println("Выполнение метода")
    }

    fun oneBefore() {
        println("Что то пошло не так и запустился не тот метод")
    }

    fun oneAfter() {
        println("Что то пошло не так и запустился не тот метод")
    }

    fun afterOne() {
        println("Выполнение after метода")
    }

    fun afterTwo() {
        println("Выполнение after метода")
    }
}