package com.alifalpian.krakatauapp.presentation.technician.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.domain.model.TechnicianDashboardEquipment
import com.alifalpian.krakatauapp.presentation.destinations.HomeTechnicianScreenDestination
import com.alifalpian.krakatauapp.presentation.destinations.LoginScreenDestination
import com.alifalpian.krakatauapp.ui.components.dashboard.DashboardTechnicianEquipmentItem
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauDashboardTopAppBar
import com.alifalpian.krakatauapp.ui.components.maintenance.ShimmerMaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

private const val TAG = "DashboardTechnicianScre"

@ExperimentalFoundationApi
@Destination
@ExperimentalMaterial3Api
@Composable
fun DashboardTechnicianScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    viewModel: DashboardTechnicianViewModel = hiltViewModel(),
) {

    val dashboardTechnicianUiState by viewModel.dashboardTechnicianUiState.collectAsState()
    val loggedUser = dashboardTechnicianUiState.loggedUser
    val dashboardEquipments = dashboardTechnicianUiState.dashboardEquipments
    val user = dashboardTechnicianUiState.user
    val signOut = dashboardTechnicianUiState.signOut

    val logoutDialogUseCase = rememberUseCaseState()

    val navigateToLoginScreen: () -> Unit = {
        navigator.navigate(LoginScreenDestination()) {
            popUpTo(HomeTechnicianScreenDestination.route) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getLoggedUser()
    }

    LaunchedEffect(key1 = loggedUser) {
        if (loggedUser != null) {
            viewModel.getUser(loggedUser.uid)
        }
    }

    LaunchedEffect(key1 = user) {
        if (user is Resource.Success) {
            viewModel.getTechnicianDashboardEquipments(user.data.documentId)
        }
    }

    LaunchedEffect(key1 = signOut) {
        if (signOut is Resource.Success) {
            navigateToLoginScreen()
        }
    }

    InfoDialog(
        state = logoutDialogUseCase,
        selection = InfoSelection(
            positiveButton = SelectionButton(
                text = "Keluar"
            ),
            onPositiveClick = viewModel::signOut,
            negativeButton = SelectionButton(
                text = "Batal"
            ),
            onNegativeClick = logoutDialogUseCase::finish
        ),
        body = InfoBody.Default(bodyText = "Apakah kamu yakin keluar dari aplikasi?")
    )

    Scaffold(
        topBar = {
            KrakatauDashboardTopAppBar(
                user = user,
                onLogoutClicked = logoutDialogUseCase::show
            )
        },
        modifier = modifier
    ) { paddingValues ->
        DashboardTechnicianContent(
            modifier = Modifier.padding(paddingValues),
            dashboardEquipments = dashboardEquipments
        )
    }
}

@Composable
fun DashboardTechnicianContent(
    modifier: Modifier = Modifier,
    dashboardEquipments: Resource<List<TechnicianDashboardEquipment>>,
    onItemClicked: () -> Unit = {}
) {
    if (dashboardEquipments is Resource.Empty) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Tidak ada data!",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        if (dashboardEquipments is Resource.Loading) {
            items(10) {
                ShimmerMaintenanceTechnicianItem()
            }
        }
        if (dashboardEquipments is Resource.Success) {
            items(items = dashboardEquipments.data, key = { it.id }) {
                DashboardTechnicianEquipmentItem(
                    equipment = it,
                    onClicked = { onItemClicked() }
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewDashboardTechnicianScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            DashboardTechnicianScreen()
        }
    }
}
