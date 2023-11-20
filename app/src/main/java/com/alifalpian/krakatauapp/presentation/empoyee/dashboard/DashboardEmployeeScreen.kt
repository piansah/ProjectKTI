package com.alifalpian.krakatauapp.presentation.empoyee.dashboard

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.presentation.destinations.HomeEmployeeScreenDestination
import com.alifalpian.krakatauapp.presentation.destinations.LoginScreenDestination
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauDashboardTopAppBar
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.components.maintenance.ShimmerMaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.util.emptyString
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@ExperimentalFoundationApi
@Destination
@ExperimentalMaterial3Api
@Composable
fun DashboardEmployeeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    viewModel: DashboardEmployeeViewModel = hiltViewModel()
) {

    val dashboardEmployeeUiState by viewModel.dashboardEmployeeUiState.collectAsState()
    val loggedUser = dashboardEmployeeUiState.loggedUser
    val user = dashboardEmployeeUiState.user
    val equipments = dashboardEmployeeUiState.equipments
    val signOut = dashboardEmployeeUiState.signOut

    val errorDialogUseCase = rememberUseCaseState()
    val logoutDialogUseCase = rememberUseCaseState()

    var errorMessage by remember { mutableStateOf("") }

    val navigateToLoginScreen: () -> Unit = {
        navigator.navigate(LoginScreenDestination()) {
            popUpTo(HomeEmployeeScreenDestination.route) {
                inclusive = true
            }
        }
    }

    val onEquipmentClicked: (Equipment) -> Unit = {}

    LaunchedEffect(key1 = Unit) {
        viewModel.getLoggedUser()
    }

    LaunchedEffect(key1 = loggedUser) {
        if (loggedUser != null) {
            viewModel.getUser(loggedUser.uid)
            viewModel.getEmployeeEquipments(loggedUser.uid)
        }
    }

    LaunchedEffect(key1 = user, key2 = equipments) {
        if (user is Resource.Error) {
            errorMessage = user.error ?: emptyString()
            errorDialogUseCase.show()
        }
        if (equipments is Resource.Error) {
            errorMessage = equipments.error ?: emptyString()
            errorDialogUseCase.show()
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

    InfoDialog(
        state = logoutDialogUseCase,
        selection = InfoSelection(
            positiveButton = SelectionButton(
                text = "Oke"
            ),
            onPositiveClick = logoutDialogUseCase::finish,
        ),
        body = InfoBody.Default(bodyText = errorMessage)
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
        if (equipments is Resource.Empty) {
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
            modifier = Modifier.padding(paddingValues)
        ) {
            if (equipments is Resource.Loading) {
                items(10) {
                    ShimmerMaintenanceTechnicianItem()
                }
            }
            if (equipments is Resource.Success) {
                items(items = equipments.data, key = { it.documentId }) {
                    MaintenanceTechnicianItem(
                        equipment = it,
                        onClicked = onEquipmentClicked
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewDashboardEmployeeScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            DashboardEmployeeScreen()
        }
    }
}
