package ru.tyryshkin.tracker.presentation.shared

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.tyryshkin.core.domain.entity.Distance
import ru.tyryshkin.coreui.component.RSpacer
import ru.tyryshkin.coreui.component.RWidget
import ru.tyryshkin.coreui.component.WidgetStyle
import ru.tyryshkin.coreui.component.WidgetType
import ru.tyryshkin.coreui.theme.Typography
import ru.tyryshkin.tracker.R

@Composable
fun RaceInfoContent(
    date: String?,
    time: String,
    distance: Distance
) {
    RSpacer(height = 16.dp)
    date?.let { DateWidget(date) }
    RSpacer(height = 16.dp)
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Max)
    ) {
        TimerWidget(time = time)
        RSpacer(width = 16.dp)
        DistanceWidget(distance)
    }
}

@Composable
private fun DateWidget(date: String) {
    RWidget(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        icon = R.drawable.ic_calendar,
        title = null,
        value = date,
        widgetStyle = WidgetStyle.SECONDARY,
        widgetType = WidgetType.HORIZONTAL
    )
}

@Composable
private fun RowScope.TimerWidget(time: String) {
    RWidget(
        modifier = Modifier.weight(1f).alpha(0.9f),
        icon = R.drawable.ic_time,
        title = stringResource(R.string.time),
        value = time,
        valueStyle = Typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
private fun RowScope.DistanceWidget(
    distance: Distance
) {
    RWidget(
        modifier = Modifier.weight(1f).alpha(0.9f),
        icon = R.drawable.ic_distance,
        title = stringResource(R.string.distance),
        value = distance.getDistanceWithParameterString(),
        valueStyle = Typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
    )
}
