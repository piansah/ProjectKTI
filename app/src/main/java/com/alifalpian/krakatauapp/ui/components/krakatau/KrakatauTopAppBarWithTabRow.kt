package com.alifalpian.krakatauapp.ui.components.krakatau

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun KrakatauTopAppBarWithTabRow(
    modifier: Modifier = Modifier,
    title: String,
    type: KrakatauTopAppBarType = KrakatauTopAppBarType.Default,
    onNavigationIconClicked: () -> Unit = {},
    pagerState: PagerState,
    scope: CoroutineScope,
    tabs: List<String>,
) {
    Column(
        modifier = modifier
    ) {
        KrakatauTopAppBar(
            title = title,
            type = type,
            onNavigationIconClicked = onNavigationIconClicked,
        )
        KrakatauTabRow(
            pagerState = pagerState,
            scope = scope,
            tabs = tabs
        )
    }
}