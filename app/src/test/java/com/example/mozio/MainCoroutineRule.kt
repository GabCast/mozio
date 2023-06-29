package com.example.mozio

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestRule {

    val testDispatcher = TestCoroutineDispatcher()

    override fun apply(base: Statement, description: Description?): Statement {
        return MainCoroutineStatement(base, testDispatcher)
    }
}

class MainCoroutineStatement @ExperimentalCoroutinesApi constructor(
    private val base: Statement,
    private val testDispatcher: TestCoroutineDispatcher
) : Statement() {

    @ExperimentalCoroutinesApi
    @Throws(Throwable::class)
    override fun evaluate() {
        Dispatchers.setMain(testDispatcher)
        try {
            base.evaluate() // This executes tests
        } finally {
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }
}
