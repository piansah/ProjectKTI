package com.alifalpian.krakatauapp.presentation.empoyee.maintenance.form

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.presentation.destinations.HomeEmployeeScreenDestination
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauButton
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTabRow
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBar
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBarType
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceFormCheckList
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceHeader
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceSafetyUseForm
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceSafetyUseFormType
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceTechnicianHeader
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceToolsForm
import com.alifalpian.krakatauapp.ui.components.maintenance.MaintenanceToolsFormType
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.util.emptyString
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

enum class MaintenanceFormEmployeeScreenStatus {
    WaitingForApproval, Approved
}

//@RootNavGraph(start = true)
@Destination
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MaintenanceFormEmployeeScreen(
    modifier: Modifier = Modifier,
    status: MaintenanceFormEmployeeScreenStatus = MaintenanceFormEmployeeScreenStatus.WaitingForApproval,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    viewModel: MaintenanceFormEmployeeViewModel = hiltViewModel(),
    maintenanceHistoryDocumentId: String = emptyString()
) {

    val tabPages = listOf(
        "Tools/Alat", "Penggunaan Safety"
    )
    val pagerState = rememberPagerState {
        tabPages.size
    }
    val scope = rememberCoroutineScope()

    val maintenanceFormEmployeeUiState by viewModel.maintenanceFormEmployeeUiState.collectAsState()
    val maintenanceHistory = maintenanceFormEmployeeUiState.maintenanceHistory
    val equipment = maintenanceFormEmployeeUiState.equipment
    val employee = maintenanceFormEmployeeUiState.employee
    val technician = maintenanceFormEmployeeUiState.technician
    val maintenanceCheckPoints = maintenanceFormEmployeeUiState.maintenanceCheckPoints
    val maintenanceToolsForm = maintenanceFormEmployeeUiState.maintenanceToolsForm
    val maintenanceSafetyUseForm = maintenanceFormEmployeeUiState.maintenanceSafetyUseForm
    val acceptMaintenance = maintenanceFormEmployeeUiState.acceptMaintenance
    val rejectMaintenance = maintenanceFormEmployeeUiState.rejectMaintenance

    val buttonAcceptAndRejectLoadingState by viewModel.buttonSubmitAndRejectLoadingState.collectAsState(
        initial = false
    )

    val confirmationAcceptMaintenanceFormDialogUseCase = rememberUseCaseState()
    val confirmationRejectMaintenanceFormDialogUseCase = rememberUseCaseState()
    val confirmationSuccessMaintenanceFormDialogUseCase = rememberUseCaseState()

    val editableForm = remember {
        status == MaintenanceFormEmployeeScreenStatus.WaitingForApproval
    }

    val buttonEnabled = remember {
        status == MaintenanceFormEmployeeScreenStatus.WaitingForApproval
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMaintenanceHistory(maintenanceHistoryDocumentId)
    }

    LaunchedEffect(key1 = maintenanceHistory) {
        if (maintenanceHistory is Resource.Success) {
            val data = maintenanceHistory.data
            viewModel.getEmployee(data.employeeDocumentId)
            viewModel.getTechnician(data.technicianDocumentId)
            viewModel.getEquipment(equipmentDocumentId = data.equipmentDocumentId)
            viewModel.getMaintenanceCheckPointHistory(
                checkPointId = data.maintenanceCheckPoint,
                maintenanceCheckPointHistoryDocumentId = data.maintenanceCheckPointHistoryDocumentId
            )
            viewModel.getMaintenanceTools(maintenanceHistoryDocumentId = data.documentId)
            viewModel.getMaintenanceSafetyUse(maintenanceHistoryDocumentId = data.documentId)
        }
    }

    LaunchedEffect(key1 = acceptMaintenance, key2 = rejectMaintenance) {
        if (acceptMaintenance is Resource.Success || rejectMaintenance is Resource.Success) {
            confirmationSuccessMaintenanceFormDialogUseCase.show()
        }
    }

    val onNavigationIconClicked: () -> Unit = {
        navigator.navigateUp()
    }

    val onAcceptButtonClicked: () -> Unit = { confirmationAcceptMaintenanceFormDialogUseCase.show() }
    val onRejectButtonClicked: () -> Unit = { confirmationRejectMaintenanceFormDialogUseCase.show() }

    val acceptMaintenanceEquipment: () -> Unit = {
        if (maintenanceHistory is Resource.Success) {
            viewModel.acceptMaintenanceEquipments(maintenanceHistoryDocumentId)
        }
    }
    val rejectMaintenanceEquipment: () -> Unit = {
        if (maintenanceHistory is Resource.Success) {
            viewModel.rejectMaintenanceEquipments(maintenanceHistoryDocumentId)
        }
    }

    val navigateBackToHomeScreen: () -> Unit = {
        navigator.navigate(HomeEmployeeScreenDestination()) {
            popUpTo(HomeEmployeeScreenDestination.route) {
                inclusive = true
            }
        }
    }

    CoreDialog(
        state = confirmationAcceptMaintenanceFormDialogUseCase,
        selection = CoreSelection(
            positiveButton = SelectionButton(text = "Accept"),
            onPositiveClick = acceptMaintenanceEquipment,
            negativeButton = SelectionButton("Kembali"),
            onNegativeClick = { confirmationAcceptMaintenanceFormDialogUseCase.finish() }
        ),
        body = { Text(text = "Apakah kamu yakin ingin meng accept hasil maintenance?") }
    )

    CoreDialog(
        state = confirmationRejectMaintenanceFormDialogUseCase,
        selection = CoreSelection(
            positiveButton = SelectionButton(text = "Reject"),
            onPositiveClick = rejectMaintenanceEquipment,
            negativeButton = SelectionButton("Kembali"),
            onNegativeClick = { confirmationAcceptMaintenanceFormDialogUseCase.finish() }
        ),
        body = { Text(text = "Apakah kamu yakin ingin meng reject hasil maintenance?") }
    )

    CoreDialog(
        state = confirmationSuccessMaintenanceFormDialogUseCase,
        selection = CoreSelection(
            positiveButton = SelectionButton(text = "Kembali"),
            onPositiveClick = navigateBackToHomeScreen,
            negativeButton = null
        ),
        body = { Text(text = "Maintenance berhasil disubmit!") },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )

    Scaffold(
        topBar = {
            KrakatauTopAppBar(
                title = "Maintenance",
                type = KrakatauTopAppBarType.Detail,
                onNavigationIconClicked = onNavigationIconClicked
            )
        },
        modifier = modifier.animateContentSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFEDEDED))
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    MaintenanceTechnicianHeader(
                        technician = technician,
                        maintenanceHistory = maintenanceHistory,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(top = 32.dp, bottom = 16.dp)
                    )
                }
                item { Divider() }
                item {
                    MaintenanceHeader(
                        equipment = equipment,
                        user = employee,
                        modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 32.dp, top = 16.dp)
                    )
                }
                item {
                    MaintenanceFormCheckList(
                        maintenanceCheckPoints = maintenanceCheckPoints,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 20.dp),
                        checkListEnabled = true
                    )
                }
                stickyHeader {
                    KrakatauTabRow(
                        pagerState = pagerState,
                        scope = scope,
                        tabs = tabPages
                    )
                }
                item {
                    HorizontalPager(
                        state = pagerState,
                        verticalAlignment = Alignment.Top
                    ) { position ->
                        when (position) {
                            0 -> {
                                MaintenanceToolsForm(
                                    maintenanceTools = maintenanceToolsForm,
                                    modifier = Modifier.padding(32.dp),
                                    type = MaintenanceToolsFormType.Employee,
                                )
                            }

                            1 -> {
                                MaintenanceSafetyUseForm(
                                    maintenanceSafetyUse = maintenanceSafetyUseForm,
                                    modifier = Modifier.padding(32.dp),
                                    type = MaintenanceSafetyUseFormType.Employee,
                                )
                            }
                        }
                    }
                }
            }
            Divider()
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                KrakatauButton(
                    title = "Accept",
                    onClicked = onAcceptButtonClicked,
                    modifier = Modifier.padding(18.dp),
                    enabled = buttonEnabled,
                    loading = buttonAcceptAndRejectLoadingState
                )
                KrakatauButton(
                    title = "Reject",
                    onClicked = onRejectButtonClicked,
                    modifier = Modifier.padding(18.dp),
                    enabled = buttonEnabled,
                    loading = buttonAcceptAndRejectLoadingState,
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMaintenanceFormEmployeeScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            MaintenanceFormEmployeeScreen()
        }
    }
}
