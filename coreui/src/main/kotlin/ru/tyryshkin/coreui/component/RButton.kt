package ru.tyryshkin.coreui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme

enum class ButtonType { PRIMARY, SECONDARY }

enum class Shape { DEFAULT, CIRCLE }

@Composable
fun RButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Int? = null,
    enabled: Boolean = true,
    type: ButtonType = ButtonType.PRIMARY,
    shape: Shape = Shape.DEFAULT,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation()
) {
    val buttonColors = getColors(type)
    val buttonShape = RoundedCornerShape(
        if (shape != Shape.CIRCLE) 10.dp else 50.dp
    )
    val buttonModifier = if (shape != Shape.CIRCLE) {
        modifier.fillMaxWidth()
    } else {
        modifier.size(100.dp)
    }
    Button(
        modifier = buttonModifier,
        onClick = onClick,
        shape = buttonShape,
        enabled = enabled,
        colors = buttonColors,
        elevation = elevation
    ) {
        when (shape) {
            Shape.DEFAULT -> {
                if (icon != null) {
                    ButtonIcon(icon = icon, type = type, iconSize = 16.dp)
                    RSpacer(width = 8.dp)
                }
                ButtonText(title = title)
            }

            Shape.CIRCLE -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (icon != null) {
                        ButtonIcon(icon = icon, type = type, iconSize = 30.dp)
                        RSpacer(height = 8.dp)
                    }
                    ButtonText(title = title)
                }
            }
        }
    }
}

@Composable
private fun ButtonIcon(icon: Int, type: ButtonType, iconSize: Dp) {
    RIcon(
        modifier = Modifier.size(iconSize),
        icon = icon,
        tint = when (type) {
            ButtonType.PRIMARY -> MaterialTheme.colorScheme.onPrimary
            ButtonType.SECONDARY -> MaterialTheme.colorScheme.onSecondary
        }
    )
}

@Composable
private fun ButtonText(title: String) {
    RText(text = title)
}

@Composable
private fun getColors(type: ButtonType): ButtonColors = when (type) {
    ButtonType.PRIMARY -> ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
        disabledContentColor = MaterialTheme.colorScheme.secondary
    )

    ButtonType.SECONDARY -> ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
        disabledContentColor = MaterialTheme.colorScheme.secondary
    )
}

@Preview
@Composable
private fun RButtonPreview() = RTheme {
    Column {
        RButton(
            title = "Back",
            onClick = {},
            enabled = true,
            modifier = Modifier
        )
        RButton(
            title = "Next",
            onClick = {},
            enabled = true,
            modifier = Modifier,
            type = ButtonType.SECONDARY
        )
        RButton(
            title = "Back",
            onClick = {},
            icon = R.drawable.ic_error,
            enabled = true,
            modifier = Modifier
        )
        RButton(
            title = "Next",
            onClick = {},
            icon = R.drawable.ic_success,
            enabled = true,
            modifier = Modifier,
            type = ButtonType.SECONDARY
        )
    }
}

@Preview
@Composable
private fun RCircleButtonPreview() = RTheme {
    Row {
        RButton(
            title = "Start",
            onClick = {},
            icon = R.drawable.ic_start,
            enabled = true,
            modifier = Modifier,
            shape = Shape.CIRCLE,
            type = ButtonType.SECONDARY
        )
        RButton(
            title = "Next",
            onClick = {},
            enabled = false,
            modifier = Modifier,
            shape = Shape.CIRCLE
        )
    }
}
