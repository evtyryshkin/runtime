package ru.tyryshkin.coreui.component

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = DividerDefaults.color
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}
