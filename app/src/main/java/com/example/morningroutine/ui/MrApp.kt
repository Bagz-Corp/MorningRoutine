package com.example.morningroutine.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.morningroutine.core.MrNavigationScaffold
import com.example.morningroutine.navigation.MrNavHost
import com.example.morningroutine.navigation.TopLevelDestinations
import com.example.morningroutine.ui.settings.SettingsDialog

@Composable
fun MrApp(
    modifier: Modifier = Modifier,
) {
    val appState = rememberMrAppState()
    val currentDestination = appState.currentTopLevelDestination
    var showSettingsDialog by remember { mutableStateOf(false) }
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    var showSnackBar by remember { mutableStateOf(false) }

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            currentDestination?.let {
                MrTopBar(
                    currentDestination = currentDestination,
                    modifier = modifier,
                    onNavigationClick = { appState.navigateBack() },
                    onActionClick = { showSettingsDialog = true },
                )
            }
         },
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        if(showSettingsDialog) {
            SettingsDialog(
                modifier = modifier,
                onDismiss = {
                    showSettingsDialog = false
                },
                onConfirm = {
                    showSettingsDialog = false
                    showSnackBar = true
                }
            )
        }

        MrNavHost(
            navController = appState.navController,
            modifier = modifier.padding(padding)
        )

        LaunchedEffect(key1 = showSnackBar) {
            if (showSnackBar) {
                snackBarHostState.showSnackbar(
                    message = "Preferences cleared"
                )
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MrTopBar(
    modifier: Modifier,
    currentDestination: TopLevelDestinations,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = currentDestination.titleTextId),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        navigationIcon = {
            if (currentDestination != TopLevelDestinations.HOME) {
                IconButton(
                    onClick = onNavigationClick,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    Scaffold(
        topBar = {
            MrTopBar(modifier = Modifier, currentDestination = TopLevelDestinations.HOME)
        }
    ) { innerPadding ->
        MrNavigationScaffold(
            modifier = Modifier.padding(innerPadding),
            navigationSuiteItems = {
                item(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(imageVector = TopLevelDestinations.HOME.selectedIcon, contentDescription = null)
                    }
                )
                item(
                    selected = false,
                    onClick = {},
                    icon = {
                        Icon(imageVector = TopLevelDestinations.FINANCE.selectedIcon, contentDescription = null)
                    }
                )
            }
        ) {
            Text(text = "Preview")
        }
    }
}