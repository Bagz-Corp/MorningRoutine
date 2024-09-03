package com.example.morningroutine.core

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

    NavigationSuiteScaffold(
        navigationSuiteItems = { MrNavigationSuiteScope(this).run(navigationSuiteItems) },
        layoutType = layoutType,
        modifier = modifier,
        containerColor = Color.Transparent,
    ) {
        content()
    }
}

class MrNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
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
    )
}