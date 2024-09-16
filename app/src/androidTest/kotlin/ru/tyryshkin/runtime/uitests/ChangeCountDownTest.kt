package ru.tyryshkin.runtime.uitests

import android.Manifest
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.rule.GrantPermissionRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.runtime.presentation.MainActivity
import ru.tyryshkin.runtime.presentation.main.MainScreen
import javax.inject.Inject

@HiltAndroidTest
class ChangeCountDownTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mRuntimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var snackbarController: SnackbarController

    @Before
    fun init() {
        hiltRule.inject()

        composeTestRule.activity.setContent {
            val navHostController = rememberNavController()
            MainScreen(
                navController = navHostController,
                errorHandler = errorHandler,
                snackbarController = snackbarController
            )
        }
    }

    @Test
    fun change_count_down_value() {
        val defaultSecondsValue = 3
        val expectedChangedSecondsValue = 10

        // Buttons for click
        val startButton = composeTestRule.onNodeWithTag(TestTag.Tracker.BUTTON_START)
        val settingsTab = composeTestRule.onNodeWithTag(TestTag.BottomNavigationTab.SETTINGS)
        val trackerTab = composeTestRule.onNodeWithTag(TestTag.BottomNavigationTab.TRACKER)
        val trackerSettingsCell = composeTestRule.onNodeWithTag(TestTag.Settings.TRACKER)
        val countDownSettingsCell = composeTestRule.onNodeWithTag(TestTag.Settings.Tracker.COUNT_DOWN)
        val tenSecondsCountDownCell = composeTestRule.onNodeWithTag("${TestTag.Settings.Tracker.MODAL_BOTTOM_SHEET_COUNT_DOWN_VALUE}$expectedChangedSecondsValue")
        val backButton = composeTestRule.onNodeWithTag(TestTag.Component.AppBar.BUTTON_BACK)

        // TODO remove all Thread after passing project
        with(composeTestRule) {
            // Ожидаем формирования экрана
            waitForIdle()
            Thread.sleep(2000)
            waitUntil(5000L) { startButton.isDisplayed() }

            // Переходим в раздел настройки
            settingsTab.performClick()
            waitForIdle()
            Thread.sleep(2000)

            // Переходим в раздел настройки Трекера
            trackerSettingsCell.performClick()
            waitForIdle()
            Thread.sleep(2000)

            // Сверяем текущее дефолтное значение
            onNode(
                matcher = hasTestTag(TestTag.Component.Cell.SUBTITLE) and
                    hasText("$defaultSecondsValue секунды") and // TODO сверять со строкой из ресурса
                    hasParent(hasTestTag(TestTag.Settings.Tracker.COUNT_DOWN)),
                useUnmergedTree = true
            ).assertExists()

            // Нажимаем на изменение настроек обратного отсчёта
            countDownSettingsCell.performClick()
            waitForIdle()
            Thread.sleep(2000)

            // Выбираем ожидаемое значение обратного отсчета
            tenSecondsCountDownCell.performClick()
            waitForIdle()
            Thread.sleep(2000)

            // Сверяем, что значение изменилось
            onNode(
                matcher = hasTestTag(TestTag.Component.Cell.SUBTITLE) and
                    hasText("$expectedChangedSecondsValue секунд") and // TODO сверять со строкой из ресурса
                    hasParent(hasTestTag(TestTag.Settings.Tracker.COUNT_DOWN)),
                useUnmergedTree = true
            ).assertExists()

            // Переходим назад
            backButton.performClick()
            waitForIdle()
            Thread.sleep(2000)

            // Переходим в раздел Трекера
            trackerTab.performClick()
            waitForIdle()

            // Нажимаем Старт
            startButton.performClick()
            waitForIdle()

            // Сверяем, что перед стартом обратный отсчёт стартует с ожидамего значения секунд
            onNodeWithTag(TestTag.Tracker.Race.TEXT_COUNT_DOWN).assert(hasText("$expectedChangedSecondsValue"))
            Thread.sleep(2000)
        }
    }
}
