package com.alifalpian.krakatauapp.ui.components.krakatau

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

enum class KrakatauTopAppBarType {
    Default, Detail
}

@ExperimentalMaterial3Api
@Composable
fun KrakatauTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    type: KrakatauTopAppBarType = KrakatauTopAppBarType.Default,
    onNavigationIconClicked: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (type == KrakatauTopAppBarType.Detail) {
                IconButton(onClick = onNavigationIconClicked) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewKrakatauTopAppBar() {
    PreventiveMaintenanceTheme {
        Surface {
            Scaffold(
                topBar = {
                    KrakatauTopAppBar(
                        title = "Maintenance",
                        type = KrakatauTopAppBarType.Detail
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}
