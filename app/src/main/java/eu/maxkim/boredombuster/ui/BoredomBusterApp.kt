package eu.maxkim.boredombuster.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.maxkim.boredombuster.model.Destination

@Composable
fun BoredomBusterApp(
    modifier: Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination by derivedStateOf {
        navBackStackEntry.value?.destination?.route
            ?.let(Destination::fromString)
            ?: Destination.Home
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentDestination.title)
                },
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                onNavigate = {
                    navController.navigate(it.path) {
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            Navigation(
                modifier = modifier,
                navController = navController
            )
        }
    }
}