package ru.tyryshkin.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.coreui.component.REmptyPlaceholder
import ru.tyryshkin.coreui.component.RImage
import ru.tyryshkin.coreui.component.RScaffold
import ru.tyryshkin.coreui.component.RSpacer
import ru.tyryshkin.coreui.component.RText
import ru.tyryshkin.coreui.component.RTopAppBar
import ru.tyryshkin.coreui.theme.Typography
import ru.tyryshkin.profile.R

@Composable
fun ProfileMainScreen(
    errorHandler: ErrorHandler,
    vm: ProfileMainViewModel,
    onNavigateToRaceDetail: (Long) -> Unit
) {
    val state = vm.state.collectAsState().value

    RScaffold<ProfileMainContent>(
        state = state,
        errorHandler = errorHandler,
        onRetryClick = vm::onRetry,
        topBar = {
            RTopAppBar(stringResource(R.string.profile_title))
        }
    ) { profileContent ->
        Content(content = profileContent, onInfoClick = onNavigateToRaceDetail)
    }
}

@Composable
private fun Content(content: ProfileMainContent, onInfoClick: (Long) -> Unit) {
    UserContent()
    RSpacer(height = 16.dp)
    RText(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        text = stringResource(R.string.history_of_races),
        style = Typography.titleLarge
    )
    if (content.races.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(content.races) {
                RaceItem(it, onInfoClick)
            }
        }
    } else {
        REmptyPlaceholder(
            title = stringResource(R.string.race_description_empty_placeholder_title),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun RaceItem(info: RaceInfoUi, onInfoClick: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { onInfoClick(info.id) }
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        RText(
            text = info.date,
            color = MaterialTheme.colorScheme.onPrimary
        )
        RSpacer(height = 16.dp)
        Row {
            Info(info.distance.getDistanceWithParameterString(), stringResource(R.string.distance))
            Info(info.time, stringResource(R.string.time))
        }
    }
}

@Composable
private fun RowScope.Info(value: String, subtitle: String) {
    Column(Modifier.weight(1f)) {
        RText(
            text = value,
            style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary
        )
        RSpacer(height = 4.dp)
        RText(
            text = subtitle,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun UserContent() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        RSpacer(height = 8.dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(100.dp)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(50.dp)),
                painter = painterResource(ru.tyryshkin.coreui.R.drawable.photo) // TODO remove hardcode
            )
            RSpacer(width = 8.dp)
            Column {
                RText(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Evgenii Tyryshkin", // TODO remove hardcode
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                RText(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Расстояние за все время: 402,6 км", // TODO remove hardcode
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
