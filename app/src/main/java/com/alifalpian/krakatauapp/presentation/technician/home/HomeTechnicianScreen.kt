package com.alifalpian.krakatauapp.presentation.technician.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alifalpian.krakatauapp.R
import com.alifalpian.krakatauapp.domain.model.BottomNavigationItem
import com.alifalpian.krakatauapp.presentation.technician.dashboard.DashboardTechnicianScreen
import com.alifalpian.krakatauapp.presentation.technician.maintenance.list.ListMaintenanceTechnicianScreen
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauNavigationBar
import com.alifalpian.krakatauapp.ui.navigation.KrakatauAppScreens
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

//@RootNavGraph(start = true)
@Destination
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun HomeTechnicianScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {
    val navController = rememberNavController()
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem("dashboard", R.drawable.ic_menu_home, "Dashboard"),
            BottomNavigationItem("maintenance", R.drawable.ic_menu_maintenance, "Maintenance"),
        )
    }
    Scaffold(
        bottomBar = {
            KrakatauNavigationBar(navController, bottomNavigationItems)
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = KrakatauAppScreens.DashboardScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(KrakatauAppScreens.DashboardScreen.route) {
                DashboardTechnicianScreen(navigator = navigator)
            }
            composable(KrakatauAppScreens.MaintenanceScreen.route) {
                ListMaintenanceTechnicianScreen(navigator = navigator)
            }
        }
    }
}


