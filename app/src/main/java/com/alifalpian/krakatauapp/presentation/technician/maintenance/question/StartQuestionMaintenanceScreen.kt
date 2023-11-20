package com.alifalpian.krakatauapp.presentation.technician.maintenance.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.alifalpian.krakatauapp.presentation.destinations.MaintenanceFormTechnicianScreenDestination
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauButton
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBar
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBarType
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import com.alifalpian.krakatauapp.util.emptyString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@ExperimentalFoundationApi
@Destination
@ExperimentalMaterial3Api
@Composable
fun StartQuestionMaintenanceScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    equipmentDocumentId: String = emptyString(),
    equipmentWillMaintenanceDocumentId: String = emptyString(),
    technicianDocumentId: String = emptyString()
) {
    val onNavigationIconClicked: () -> Unit = {
        navigator.navigateUp()
    }
    val onStartMaintenanceButtonClicked: () -> Unit = {
        navigator.navigate(MaintenanceFormTechnicianScreenDestination(
            equipmentDocumentId = equipmentDocumentId,
            technicianDocumentId = technicianDocumentId,
            equipmentWillMaintenanceDocumentId = equipmentWillMaintenanceDocumentId
        ))
    }
    Scaffold(
        topBar = {
            KrakatauTopAppBar(
                title = "Maintenance",
                type = KrakatauTopAppBarType.Detail,
                onNavigationIconClicked = onNavigationIconClicked
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Apakah anda ingin melakukan Maintenance?",
                textAlign = TextAlign.Center,
                lineHeight = 1.27.em,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(28.dp))
            KrakatauButton(
                title = "Mulai",
                onClicked = onStartMaintenanceButtonClicked
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewStartQuestionMaintenanceScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            StartQuestionMaintenanceScreen()
        }
    }
}