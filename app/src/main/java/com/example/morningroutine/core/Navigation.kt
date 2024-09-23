package com.example.morningroutine.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MrNavigationScaffold(
    modifier: Modifier = Modifier,
    navigationSuiteItems: MrNavigationSuiteScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val layoutType: NavigationSuiteType = NavigationSuiteType.NavigationBar

    val itemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = MrNavigationDefaults.navigationIndicatorColor(),
            selectedIconColor = MrNavigationDefaults.navigationSelectedItemColor(),
            selectedTextColor = MrNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MrNavigationDefaults.navigationContentColor(),
            unselectedTextColor = MrNavigationDefaults.navigationContentColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors()
    )


    NavigationSuiteScaffold(
        navigationSuiteItems = {
            MrNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteScopeItemColors = itemColors
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        modifier = modifier,
        containerColor = Color.Transparent,
    ) {
        content()
    }
}

class MrNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteScopeItemColors: NavigationSuiteItemColors
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        modifier = modifier,
        colors = navigationSuiteScopeItemColors
    )
}

/**
 * Navigation default values.
 */
object MrNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}