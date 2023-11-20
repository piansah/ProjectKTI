package com.alifalpian.krakatauapp.presentation.empoyee.maintenance.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.presentation.destinations.MaintenanceFormEmployeeScreenDestination
import com.alifalpian.krakatauapp.presentation.empoyee.maintenance.form.MaintenanceFormEmployeeScreenStatus
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBarWithTabRow
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.components.maintenance.ShimmerMaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

//@RootNavGraph(start = true)
@Destination
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun ListMaintenanceEmployeeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    viewModel: ListMaintenanceEmployeeViewModel = hiltViewModel(),
    uid: String = "x8JE1nEpHPMLnC1QYlDXy7Ax0Kz2"
) {
    val tabPager = listOf(
        "Semua", "Selesai"
    )
    val pagerState = rememberPagerState {
        tabPager.size
    }
    val scope = rememberCoroutineScope()

    val listMaintenanceEmployeeUiState by viewModel.dashboardEmployeeUiState.collectAsState()
    val user = listMaintenanceEmployeeUiState.user
    val waitingForApprovalMaintenance = listMaintenanceEmployeeUiState.waitingForApprovalMaintenance
    val hasBeenApprovedMaintenance = listMaintenanceEmployeeUiState.hasBeenApprovedMaintenance


    LaunchedEffect(key1 = Unit) {
        viewModel.getUser(uid)
    }

    LaunchedEffect(key1 = user) {
        if (user is Resource.Success) {
            viewModel.getWaitingForApprovalEquipmentsMaintenance(user.data.documentId)
            viewModel.getHasBeenApprovedEquipmentsMaintenance(user.data.documentId)
        }
    }

    val onAllMaintenanceEquipmentClicked: (Equipment) -> Unit = {
        navigator.navigate(MaintenanceFormEmployeeScreenDestination(
            status = MaintenanceFormEmployeeScreenStatus.WaitingForApproval,
            maintenanceHistoryDocumentId = it.maintenanceHistoryDocumentId
        ))
    }

    val onFinishedMaintenanceEquipmentClicked: (Equipment) -> Unit = {
        navigator.navigate(MaintenanceFormEmployeeScreenDestination(
            status = MaintenanceFormEmployeeScreenStatus.Approved,
            maintenanceHistoryDocumentId = it.maintenanceHistoryDocumentId
        ))
    }

    Scaffold(
        topBar = {
            KrakatauTopAppBarWithTabRow(
                title = "Maintenance",
                onNavigationIconClicked = {},
                pagerState = pagerState,
                scope = scope,
                tabs = tabPager
            )
        },
        modifier = modifier
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ListWaitingForApprovalMaintenance(
                    equipments = waitingForApprovalMaintenance,
                    onEquipmentClicked = onAllMaintenanceEquipmentClicked
                )
                1 -> ListHasBeenApprovedMaintenance(
                    equipments = hasBeenApprovedMaintenance,
                    onEquipmentClicked = onFinishedMaintenanceEquipmentClicked
                )
            }
        }
    }
}

@Composable
private fun ListWaitingForApprovalMaintenance(
    modifier: Modifier = Modifier,
    equipments: Resource<List<Equipment>>,
    onEquipmentClicked: (Equipment) -> Unit = {}
) {
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
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (equipments is Resource.Loading) {
            items(10) {
                ShimmerMaintenanceTechnicianItem()
            }
        }
        if (equipments is Resource.Success) {
            items(items = equipments.data, key = { it.documentId }) { equipment ->
                MaintenanceTechnicianItem(
                    equipment = equipment,
                    onClicked = onEquipmentClicked
                )
            }
        }
    }
}

@Composable
private fun ListHasBeenApprovedMaintenance(
    modifier: Modifier = Modifier,
    equipments: Resource<List<Equipment>>,
    onEquipmentClicked: (Equipment) -> Unit = {}
) {
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
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (equipments is Resource.Loading) {
            items(10) {
                ShimmerMaintenanceTechnicianItem()
            }
        }
        if (equipments is Resource.Success) {
            items(items = equipments.data, key = { it.maintenanceHistoryDocumentId }) { equipment ->
                MaintenanceTechnicianItem(
                    equipment = equipment,
                    onClicked = onEquipmentClicked
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewListMaintenanceEmployeeScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            ListMaintenanceEmployeeScreen()
        }
    }
}
