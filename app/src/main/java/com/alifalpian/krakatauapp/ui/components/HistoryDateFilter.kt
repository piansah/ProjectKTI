package com.alifalpian.krakatauapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.R
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme
import java.time.LocalDate
import java.util.Locale

@Composable
fun HistoryDateFilter(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onFilterIconClicked: () -> Unit = {}
) {
    val month = date.month.name.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
    val year = date.year
    Column(
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Bulan",
                    lineHeight = 1.67.em,
                    fontSize = 12.sp,
                    letterSpacing = 0.1.sp
                )
                Text(
                    text = "$month $year",
                    lineHeight = 1.43.em,
                    fontSize = 14.sp,
                    letterSpacing = 0.1.sp
                )
            }
            IconButton(onClick = onFilterIconClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_calendar_month_24),
                    contentDescription = "Pick date"
                )
            }
        }
        Divider()
    }
}

@Preview
@Composable
fun PreviewHistoryDateFilter() {
    PreventiveMaintenanceTheme {
        Surface {
            val localDate = LocalDate.now()
            HistoryDateFilter(
                date = localDate,
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}