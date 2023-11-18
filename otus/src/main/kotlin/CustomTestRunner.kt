class CustomTestRunner<T : ExampleTestStep> : TestRunner<T> {
    override fun runTest(steps: T, test: () -> Unit) {
        steps.javaClass.methods.filter { it.name.startsWith("before") }
            .forEach {
                it.invoke(steps)
            }

        println("....Запуск теста.....")
        test.invoke()

        steps.javaClass.methods.filter { it.name.startsWith("after") }
            .forEach {
                println("....Запуск after метода....")
                it.invoke(steps)
            }
    }
}