fun main(args: Array<String>) {
    val testStep = ExampleTestStep()
    val testRunner: TestRunner<ExampleTestStep> = CustomTestRunner()

    testRunner.runTest(testStep) {
        println("Выполнение теста")
    }
}