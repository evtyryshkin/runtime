package ru.tyryshkin.runtime.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * TestRunner need to use Hilt Application in our test
 * It is configured in build.gradle app - testInstrumentationRunner = "ru.tyryshkin.runtime.utils.TestRunner"
 */
class TestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
