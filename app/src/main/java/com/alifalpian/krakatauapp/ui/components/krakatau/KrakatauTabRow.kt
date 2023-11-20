package com.alifalpian.krakatauapp.ui.components.krakatau

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun KrakatauTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    scope: CoroutineScope,
    tabs: List<String>,
) {
    val onTabClicked: (Int) -> Unit = {
        scope.launch {
            pagerState.animateScrollToPage(it)
        }
    }
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 4.dp,
                color = Color.White
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabClicked(index) },
                text = { Text(text = tab) }
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewKrakatauTabRow() {
    PreventiveMaintenanceTheme {
        Surface {
            val tabPages = listOf(
                "Tools/Alat", "Penggunaan Safety"
            )
            val pagerState = rememberPagerState {
                tabPages.size
            }
            val scope = rememberCoroutineScope()
            Column {
                KrakatauTabRow(
                    pagerState = pagerState,
                    scope = scope,
                    tabs = tabPages
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        when (it) {
                            0 -> Text(text = "Page 0")
                            1 -> Text(text = "Page 1")
                        }
                    }
                }
            }

        }
    }
}
