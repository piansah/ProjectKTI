package com.alifalpian.krakatauapp.presentation.technician.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifalpian.krakatauapp.domain.model.MaintenanceEquipment
import com.alifalpian.krakatauapp.ui.components.HistoryDateFilter
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauTopAppBar
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

@Destination
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun HistoryTechnicianScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val dummyMaintenanceEquipments = (1..10).map {
        MaintenanceEquipment(
            id = it.toString(),
            order = "2210043175",
            date = "09/12/2023",
            interval = "4 MON",
            execution = "PG IT",
            location = "Ruang Staff SEKPER (WTP)",
            equipmentName = "LAPTOP DELL LATITUDE 3420 SKP4",
            technicianName = "Hasan Maulana"
        )
    }

    val calendarDialogUseCase = rememberUseCaseState()

    val showCalendar: () -> Unit = { calendarDialogUseCase.show() }

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
            KrakatauTopAppBar(
                title = "History"
            )
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                HistoryDateFilter(date = LocalDate.now(), onFilterIconClicked = showCalendar)
            }
            items(items = dummyMaintenanceEquipments, key = { it.id }) {
//                MaintenanceTechnicianItem(equipment = it)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewHistoryTechnicianScreen() {
    PreventiveMaintenanceTheme {
        Surface {
            HistoryTechnicianScreen()
        }
    }
}
