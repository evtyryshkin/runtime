package ru.tyryshkin.coreui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.coreui.theme.Typography

enum class WidgetStyle {
    PRIMARY, SECONDARY
}

enum class WidgetType {
    VERTICAL, HORIZONTAL
}

@Composable
fun RWidget(
    @DrawableRes icon: Int,
    title: String?,
    value: String,
    modifier: Modifier = Modifier,
    valueStyle: TextStyle = Typography.titleMedium,
    widgetStyle: WidgetStyle = WidgetStyle.PRIMARY,
    widgetType: WidgetType = WidgetType.VERTICAL
) {
    when (widgetType) {
        WidgetType.VERTICAL -> VerticalWidget(icon, title, value, modifier, valueStyle, widgetStyle)
        WidgetType.HORIZONTAL -> HorizontalWidget(icon, title, value, modifier, valueStyle, widgetStyle)
    }
}

@Composable
fun HorizontalWidget(
    icon: Int,
    title: String?,
    value: String,
    modifier: Modifier,
    valueStyle: TextStyle,
    widgetStyle: WidgetStyle
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(
                when (widgetStyle) {
                    WidgetStyle.PRIMARY -> MaterialTheme.colorScheme.primary
                    WidgetStyle.SECONDARY -> MaterialTheme.colorScheme.secondary
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RIcon(
            icon = icon,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        RSpacer(width = 16.dp)
        if (title != null) {
            RText(
                text = "$title:",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        RSpacer(width = 4.dp)
        RText(
            text = value,
            color = MaterialTheme.colorScheme.onPrimary,
            style = valueStyle
        )
    }
}

@Composable
private fun VerticalWidget(
    icon: Int,
    title: String?,
    value: String,
    modifier: Modifier,
    valueStyle: TextStyle,
    widgetStyle: WidgetStyle
) {
    Column(
        modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(
                when (widgetStyle) {
                    WidgetStyle.PRIMARY -> MaterialTheme.colorScheme.primary
                    WidgetStyle.SECONDARY -> MaterialTheme.colorScheme.secondary
                }
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RIcon(
                icon = icon,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            if (title != null) {
                RSpacer(width = 16.dp)
                RText(text = title, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
        RSpacer(height = 16.dp)
        RText(
            text = value,
            color = MaterialTheme.colorScheme.onPrimary,
            style = valueStyle
        )
        RSpacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun RWidgetPreview() = RTheme {
    Column {
        RWidget(
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
            icon = R.drawable.ic_info,
            title = "Info",
            value = "Value"
        )
        RSpacer(height = 16.dp)
        RWidget(
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
            icon = R.drawable.ic_error,
            title = "Error",
            value = "Value",
            widgetStyle = WidgetStyle.SECONDARY
        )
        RSpacer(height = 16.dp)
        RWidget(
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
            icon = R.drawable.ic_success,
            title = "Error",
            value = "Value",
            widgetType = WidgetType.HORIZONTAL
        )
    }
}
