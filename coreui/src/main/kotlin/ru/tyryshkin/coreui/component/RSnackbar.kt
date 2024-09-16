package ru.tyryshkin.coreui.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.tyryshkin.core.informer.snackbar.SnackbarStyle
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun RSnackbar(
    message: String,
    style: SnackbarStyle?,
    duration: SnackbarDuration,
    withDismissAction: Boolean,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {
    val snackbarModifier = if (duration != SnackbarDuration.Indefinite) modifier.updateForDraggable(onDismiss) else modifier
    Snackbar(
        modifier = snackbarModifier.padding(8.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onBackground,
        action = {
            if (actionLabel != null && onActionClick != null) {
                TextButton(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = {
                        onActionClick()
                        onDismiss()
                    }
                ) {
                    RText(
                        text = actionLabel,
                        style = Typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actionContentColor = MaterialTheme.colorScheme.surfaceVariant,
        dismissActionContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        dismissAction = {
            if (withDismissAction) {
                IconButton(onDismiss) {
                    RIcon(
                        icon = R.drawable.ic_close,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actionOnNewLine = true
    ) {
        MessageRow(
            message = message,
            style = style,
            withoutAnyAction = actionLabel == null && onActionClick == null && !withDismissAction
        )
    }
}

@Composable
fun MessageRow(
    message: String,
    style: SnackbarStyle?,
    withoutAnyAction: Boolean
) {
    var textLineCount by remember { mutableIntStateOf(0) }
    val textMeasurer = rememberTextMeasurer()

    Column {
        SubcomposeLayout { layoutConstraints ->
            val verticalAlignment = if (textLineCount == 1) Alignment.CenterVertically else Alignment.Top
            layout(layoutConstraints.maxWidth, layoutConstraints.minHeight) {
                val contentPlaceables = subcompose("message") {
                    Row(verticalAlignment = verticalAlignment) {
                        if (style != null) {
                            RIcon(
                                icon = getIcon(style),
                                tint = getColor(style)
                            )
                        }
                        RSpacer(width = 8.dp)
                        BoxWithConstraints {
                            val textStyle = Typography.bodyLarge
                            val textLayoutResult = textMeasurer.measure(
                                text = message,
                                style = textStyle,
                                constraints = constraints
                            )
                            textLineCount = textLayoutResult.lineCount
                            RText(
                                text = message,
                                style = textStyle
                            )
                        }
                    }
                }.map { it.measure(layoutConstraints) }

                var yPosition = 0
                contentPlaceables.forEach {
                    it.placeRelative(0, yPosition)
                    yPosition += it.height
                }
            }
        }
        if (withoutAnyAction && textLineCount == 1) RSpacer(height = 34.dp)
    }
}

@ReadOnlyComposable
private fun getIcon(style: SnackbarStyle): Int = when (style) {
    SnackbarStyle.INFO -> R.drawable.ic_info
    SnackbarStyle.SUCCESS -> R.drawable.ic_success
    SnackbarStyle.ERROR -> R.drawable.ic_error
}

@Composable
@ReadOnlyComposable
private fun getColor(style: SnackbarStyle): Color = when (style) {
    SnackbarStyle.INFO, SnackbarStyle.SUCCESS -> MaterialTheme.colorScheme.primary
    SnackbarStyle.ERROR -> MaterialTheme.colorScheme.error
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Modifier.updateForDraggable(onDismiss: () -> Unit): Modifier {
    val draggableState = rememberAnchorDraggable(LocalDensity.current)
    LaunchedEffect(draggableState.currentValue) {
        if (draggableState.currentValue in listOf(DragAnchors.Left, DragAnchors.Right)) onDismiss()
    }

    return this
        .offset {
            IntOffset(
                x = draggableState
                    .requireOffset()
                    .roundToInt(),
                y = 0
            )
        }
        .anchoredDraggable(draggableState, Orientation.Horizontal)
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun rememberAnchorDraggable(density: Density) = remember {
    AnchoredDraggableState(
        initialValue = DragAnchors.Start,
        positionalThreshold = { with(density) { 56.dp.toPx() } },
        velocityThreshold = { with(density) { 56.dp.toPx() } },
        animationSpec = tween()
    ).apply {
        updateAnchors(
            DraggableAnchors {
                DragAnchors.Start at 0f
                DragAnchors.Left at -200f
                DragAnchors.Right at 200f
            }
        )
    }
}

enum class DragAnchors { Start, Left, Right }
