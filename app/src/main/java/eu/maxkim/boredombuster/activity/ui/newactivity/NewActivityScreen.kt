package eu.maxkim.boredombuster.activity.ui.newactivity

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.maxkim.boredombuster.R
import eu.maxkim.boredombuster.Tags
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.ui.accessibilityLabelRes
import eu.maxkim.boredombuster.activity.ui.priceLabelRes
import eu.maxkim.boredombuster.ui.theme.Red300

@Composable
fun NewActivity(
    modifier: Modifier = Modifier,
    viewModel: NewActivityViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.padding(vertical = 32.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState = viewModel.uiState.collectAsState().value
        val uriHandler = LocalUriHandler.current

        Spacer(modifier = Modifier.weight(1f))
        when (uiState) {
            is NewActivityUiState.Loading -> LoadingCard(modifier = Modifier.fillMaxWidth())
            is NewActivityUiState.Success -> {
                NewActivityCard(
                    modifier = Modifier.fillMaxWidth(),
                    activity = uiState.activity,
                    isFavorite = uiState.isFavorite,
                    onFavoriteClick = {
                        viewModel.setIsFavorite(uiState.activity, it)
                    },
                    onLinkClick = {
                        uriHandler.openUri(uiState.activity.link)
                    }
                )
            }
            is NewActivityUiState.Error -> {
                Text(text = "Oops!: ${uiState.exception.message}")
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        FloatingActionButton(
            modifier = Modifier.size(84.dp),
            onClick = { viewModel.loadNewActivity() },
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Icon(
                Icons.Filled.Refresh,
                modifier = Modifier
                    .size(84.dp)
                    .padding(all = 20.dp),
                contentDescription = stringResource(id = R.string.cd_refresh_activity),
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun NewActivityCard(
    modifier: Modifier,
    activity: Activity,
    isFavorite: Boolean,
    onFavoriteClick: (isFavorite: Boolean) -> Unit,
    onLinkClick: (link: String) -> Unit
) {
    Card(
        modifier = modifier.aspectRatio(1.33f),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = activity.type.toString())
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onFavoriteClick(!isFavorite)
                    }
                ) {
                    val vectorIcon = if (isFavorite)
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder

                    val contentDescriptionId = if (isFavorite) {
                        R.string.cd_delete_activity
                    } else {
                        R.string.cd_save_activity
                    }
                    Icon(
                        imageVector = vectorIcon,
                        tint = Red300,
                        contentDescription = stringResource(id = contentDescriptionId)
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(0.5f))

            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = activity.name,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.W300
            )

            if (activity.link.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .testTag(Tags.ActivityLink),
                    onClick = {
                        onLinkClick(activity.link)
                    }
                ) {
                    Text(
                        text = activity.link,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                Row(modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(
                        Icons.Default.Face,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = activity.participantCount.toString(),
                        fontWeight = FontWeight.W500,
                    )
                }

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = activity.priceLabelRes),
                    fontWeight = FontWeight.W500,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = stringResource(id = activity.accessibilityLabelRes),
                    fontWeight = FontWeight.W500,
                )
            }
        }
    }
}

@Composable
fun LoadingCard(
    modifier: Modifier
) {
    Card(
        modifier = modifier.aspectRatio(1.33f),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 3.dp
    ) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Preview
@Composable
fun Preview_NewActivityCard() {
    val activity = Activity(
        name = "Learn to dance",
        type = Activity.Type.Charity,
        participantCount = 2,
        price = 0.1f,
        accessibility = 0.2f,
        key = "234244",
        link = "www.dance.com"
    )
    MaterialTheme {
        NewActivityCard(
            modifier = Modifier.fillMaxWidth(),
            activity = activity,
            isFavorite = false,
            onFavoriteClick = { },
            onLinkClick = { }
        )
    }
}