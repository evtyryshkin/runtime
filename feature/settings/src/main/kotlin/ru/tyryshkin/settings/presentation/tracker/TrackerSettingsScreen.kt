package ru.tyryshkin.settings.presentation.tracker

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.coreui.component.RCell
import ru.tyryshkin.coreui.component.RIcon
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.coreui.component.RSpacer
import ru.tyryshkin.coreui.component.RText
import ru.tyryshkin.coreui.component.RTopAppBar
import ru.tyryshkin.coreui.theme.Typography
import ru.tyryshkin.settings.R
import ru.tyryshkin.settings.presentation.tracker.model.CountDownForSelect

@Composable
fun TrackerSettingsScreen(
    errorHandler: ErrorHandler,
    vm: TrackerSettingsViewModel,
    onNavigateToBack: () -> Unit
) {
    val state = vm.state.collectAsState().value

    RScaffold<TrackerSettingsContent>(
        state = state,
        topBar = {
            RTopAppBar(
                title = stringResource(R.string.tracker_settings_title),
                onBackClick = onNavigateToBack
            )
        },
        errorHandler = errorHandler,
        onRetryClick = vm::onRetry
    ) { content ->
        RCell(
            modifier = Modifier.testTag(TestTag.Settings.Tracker.COUNT_DOWN),
            title = stringResource(R.string.tracker_settings_count_down_before_run),
            subtitle = getCountDownValueString(content.countDownForSelect.selected),
            leadingContent = {
                RIcon(icon = R.drawable.ic_count_down)
            },
            trailingContent = {
                RIcon(
                    icon = ru.tyryshkin.coreui.R.drawable.ic_arrow_right,
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            onClick = vm::onCountDownSettingClick
        )

        ModalBottomSheetContent( // TODO вынести moddalbottomsheet в RScaffold ???
            bottomSheet = content.bottomSheet,
            countDownForSelect = content.countDownForSelect,
            onDismissBottomSheet = vm::onDismissBottomSheet,
            onSelectCountDown = vm::onSelectCountDown
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheetContent(
    bottomSheet: TrackerSettingsContent.BottomSheet,
    countDownForSelect: CountDownForSelect,
    onDismissBottomSheet: () -> Unit,
    onSelectCountDown: (Int?) -> Unit
) {
    val sheetContent: (@Composable ColumnScope.() -> Unit)? = when (bottomSheet) {
        TrackerSettingsContent.BottomSheet.Hidden -> null

        TrackerSettingsContent.BottomSheet.CountDown -> {
            { CountDownContent(countDownForSelect, onSelectCountDown) }
        }
    }

    if (sheetContent != null) {
        ModalBottomSheet( // TODO core ui
            modifier = Modifier.testTag("ModalBottomSheet"),
            onDismissRequest = onDismissBottomSheet,
            content = sheetContent
        )
    }
}

@Composable
private fun CountDownContent(
    countDownForSelect: CountDownForSelect,
    onSelectCountDown: (Int?) -> Unit
) {
    RText(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.tracker_settings_count_down_before_run_bottom_sheet_title),
        style = Typography.titleLarge
    )
    RSpacer(height = 16.dp)
    countDownForSelect.values.forEach { value ->
        val onClick = { onSelectCountDown(value) }
        RCell(
            modifier = Modifier.testTag("${TestTag.Settings.Tracker.MODAL_BOTTOM_SHEET_COUNT_DOWN_VALUE}$value"),
            title = getCountDownValueString(value),
            trailingContent = {
                RadioButton( // TODO core ui
                    selected = value == countDownForSelect.selected,
                    onClick = onClick
                )
            },
            onClick = onClick
        )
    }
    RSpacer(height = 16.dp)
}

@ReadOnlyComposable
@Composable
private fun getCountDownValueString(value: Int?) =
    if (value == null) {
        stringResource(R.string.tracker_settings_count_down_not_selected)
    } else {
        pluralStringResource(
            id = R.plurals.tracker_settings_count_down_values,
            count = value,
            value
        )
    }
