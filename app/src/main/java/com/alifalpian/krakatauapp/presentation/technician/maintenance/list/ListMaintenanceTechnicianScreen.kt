package com.alifalpian.krakatauapp.presentation.technician.maintenance.list

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alifalpian.krakatauapp.domain.model.Equipment
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.presentation.destinations.MaintenanceFormTechnicianScreenDestination
import com.alifalpian.krakatauapp.presentation.destinations.StartQuestionMaintenanceScreenDestination
import com.alifalpian.krakatauapp.presentation.technician.maintenance.form.MaintenanceFormTechnicianScreenStatus
import com.alifalpian.krakatauapp.ui.components.HistoryDateFilter
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBarWithTabRow
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.components.maintenance.ShimmerMaintenanceTechnicianItem
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDate

private const val TAG = "ListMaintenanceTechnici"

//@RootNavGraph(start = true)
@Destination
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun ListMaintenanceTechnicianScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    viewModel: ListMaintenanceTechnicianViewModel = hiltViewModel(),
) {
    val tabPager = listOf(
        "Semua", "Selesai"
    )
    val pagerState = rememberPagerState {
        tabPager.size
    }
    val scope = rememberCoroutineScope()

    val listMaintenanceTechnicianUiState by viewModel.listMaintenanceTechnicianUiState.collectAsState()
    val equipmentsWillMaintenance = listMaintenanceTechnicianUiState.equipmentsWillMaintenance
    val equipmentsHasBeenMaintenance = listMaintenanceTechnicianUiState.equipmentsHasBeenMaintenance
    val loggedUser = listMaintenanceTechnicianUiState.loggedUser
    val user = listMaintenanceTechnicianUiState.user

    val calendarDialogUseCase = rememberUseCaseState()
    val showCalendar: () -> Unit = { calendarDialogUseCase.show() }

    var technicianDocumentId by remember { mutableStateOf("") }

    val onAllMaintenanceEquipmentClicked: (Equipment) -> Unit = {
        navigator.navigate(StartQuestionMaintenanceScreenDestination(
            equipmentDocumentId = it.documentId,
            technicianDocumentId = technicianDocumentId,
            equipmentWillMaintenanceDocumentId = it.equipmentWillMaintenanceDocumentId
        ))
    }

    val onFinishedMaintenanceEquipmentClicked: (Equipment) -> Unit = {
        navigator.navigate(MaintenanceFormTechnicianScreenDestination(
            status = MaintenanceFormTechnicianScreenStatus.History,
            equipmentDocumentId = it.maintenanceHistoryDocumentId,
            maintenanceHistoryDocumentId = it.maintenanceHistoryDocumentId
        ))
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
            val documentId = user.data.documentId
            technicianDocumentId = documentId
            viewModel.getEquipmentsWillBeMaintenance(documentId)
            viewModel.getEquipmentsHasBeenMaintenance(documentId)
        }
    }

    LaunchedEffect(key1 = equipmentsWillMaintenance) {
        when (equipmentsWillMaintenance) {
            Resource.Empty -> {}
            is Resource.Error -> Log.d(
                TAG,
                "ListMaintenanceTechnicianScreen: Error = ${equipmentsWillMaintenance.error}"
            )
            Resource.Idling -> {}
            Resource.Loading -> Log.d(
                TAG,
                "ListMaintenanceTechnicianScreen: Loading"
            )

            is Resource.Success -> Log.d(
                TAG,
                "ListMaintenanceTechnicianScreen: Success = ${equipmentsWillMaintenance.data}"
            )

        }
    }

    CalendarDialog(
        state = calendarDialogUseCase,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period { startDate, endDate ->

        }
    )

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
                0 -> ListEquipmentWillMaintenance(
                    equipments = equipmentsWillMaintenance,
                    onEquipmentClicked = onAllMaintenanceEquipmentClicked
                )
                1 -> ListEquipmentHasBeenMaintenance(
                    equipments = equipmentsHasBeenMaintenance,
                    onEquipmentClicked = onFinishedMaintenanceEquipmentClicked,
                    onFilterIconClicked = showCalendar
                )
            }
        }
    }
}

@Composable
private fun ListEquipmentWillMaintenance(
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
            items(items = equipments.data, key = { it.equipmentWillMaintenanceDocumentId }) { equipment ->
                MaintenanceTechnicianItem(
                    equipment = equipment,
                    onClicked = onEquipmentClicked
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun ListEquipmentHasBeenMaintenance(
    modifier: Modifier = Modifier,
    equipments: Resource<List<Equipment>>,
    onEquipmentClicked: (Equipment) -> Unit = {},
    onFilterIconClicked: () -> Unit = {}
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
        stickyHeader {
            HistoryDateFilter(
                date = LocalDate.now(),
                onFilterIconClicked = onFilterIconClicked
            )
        }
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
fun PreviewListMaintenanceTechnicianScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            ListMaintenanceTechnicianScreen()
        }
    }
}
