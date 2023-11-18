interface TestRunner<T : ExampleTestStep> {
    fun runTest(steps: T, test: () -> Unit)
}