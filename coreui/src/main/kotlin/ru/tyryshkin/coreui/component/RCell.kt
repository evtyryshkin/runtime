package ru.tyryshkin.coreui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.coreui.theme.Typography

@Composable
fun RCell(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    showDivider: Boolean = true,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = onClick ?: {})
            .padding(start = 16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingContent != null) {
                    leadingContent()
                    RSpacer(width = 12.dp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    RText(
                        modifier = Modifier.testTag(TestTag.Component.Cell.TITLE),
                        text = title,
                        style = Typography.bodyLarge
                    )
                    if (subtitle != null) {
                        RSpacer(height = 4.dp)
                        RText(
                            modifier = Modifier.testTag(TestTag.Component.Cell.SUBTITLE),
                            text = subtitle,
                            color = MaterialTheme.colorScheme.secondary,
                            style = Typography.bodySmall
                        )
                    }
                }
                if (trailingContent != null) {
                    RSpacer(width = 12.dp)
                    trailingContent()
                }
            }
            if (showDivider) {
                RDivider()
            }
            RSpacer(height = 2.dp)
        }
    }
}

@Preview(backgroundColor = 0xffffff, showBackground = true)
@Composable
private fun RCellPreview() = RTheme {
    Column {
        RCell(
            title = "Title",
            subtitle = "Subtitle",
            leadingContent = {
                RIcon(icon = ru.tyryshkin.coreui.R.drawable.ic_info)
            },
            trailingContent = {
                RIcon(icon = ru.tyryshkin.coreui.R.drawable.ic_arrow_right)
            },
            modifier = Modifier
        )
    }
}
