class ExampleTestStep {
    fun beforeOne() {
        println("Выполнение метода")    //???? before
    }

    fun beforeTestTwo() {
        println("Выполнение метода")    //???? before
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