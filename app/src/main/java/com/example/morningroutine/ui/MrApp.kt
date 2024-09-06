package com.example.morningroutine.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.example.morningroutine.core.MrNavigationScaffold
import com.example.morningroutine.core.theme.MrTheme
import com.example.morningroutine.navigation.MrNavHost
import com.example.morningroutine.navigation.TopLevelDestinations

@Composable
fun MrApp(
    appState: MrAppState,
    modifier: Modifier = Modifier,
) {
    val currentDestination = appState.currentTopLevelDestination
    
    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            currentDestination?.let {
                MrTopBar(
                    currentDestination = currentDestination,
                    modifier = modifier
                )
            }
         },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            MrNavigationScaffold(
                navigationSuiteItems = {
                    appState.topLevelDestinations.forEach { topLevelDestination ->
                        item(
                            selected = currentDestination == topLevelDestination,
                            onClick = { appState.navigateToTopLevelDestination(topLevelDestination) },
                            icon = {
                                Icon(
                                    imageVector = topLevelDestination.selectedIcon,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            ) {
                MrNavHost(appState = appState, modifier = modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MrTopBar(
    modifier: Modifier,
    currentDestination: TopLevelDestinations
) {
    CenterAlignedTopAppBar(
        title = { stringResource(id = currentDestination.titleTextId) },
        modifier = modifier,
        navigationIcon = {
            Icon(
                imageVector = currentDestination.selectedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        actions = {
            Icon(
                imageVector = currentDestination.selectedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
    )
}

@Preview
@Composable
fun Preview() {
    MrTheme {
        // MrApp(appState = rememberMrAppState())
    }
}