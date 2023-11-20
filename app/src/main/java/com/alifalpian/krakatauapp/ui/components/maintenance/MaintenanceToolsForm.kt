package com.alifalpian.krakatauapp.ui.components.maintenance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifalpian.krakatauapp.R
import com.alifalpian.krakatauapp.domain.model.MaintenanceTools
import com.alifalpian.krakatauapp.domain.model.Resource
import com.alifalpian.krakatauapp.ui.components.krakatau.KrakatauOutlinedTextFieldWithLabel
import com.alifalpian.krakatauapp.ui.theme.PreventiveMaintenanceTheme

enum class MaintenanceToolsFormType {
    Technician, Employee
}

@ExperimentalMaterial3Api
@Composable
fun MaintenanceToolsForm(
    modifier: Modifier = Modifier,
    maintenanceTools: Resource<List<MaintenanceTools>>,
    type: MaintenanceToolsFormType = MaintenanceToolsFormType.Technician,
    onAddButtonClicked: () -> Unit = {},
    onMaintenanceFormChange: (Int, MaintenanceTools) -> Unit = { _, _ -> },
    enabled: Boolean = false
) {
    if (maintenanceTools is Resource.Success) {
        val tools = maintenanceTools.data
        val onDescriptionChanged: (Int, String) -> Unit = { index, description ->
            val maintenanceTools = tools[index].copy(
                description = description
            )
            onMaintenanceFormChange(index, maintenanceTools)
        }
        val onQuantityChanged: (Int, Int) -> Unit = { index, quantity ->
            val maintenanceTools = tools[index].copy(
                quantity = quantity
            )
            onMaintenanceFormChange(index, maintenanceTools)
        }
        val onUoMChanged: (Int, Int) -> Unit = { index, unitOfMeasurement ->
            val maintenanceTools = tools[index].copy(
                unitOfMeasurement = unitOfMeasurement
            )
            onMaintenanceFormChange(index, maintenanceTools)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE3E3E3))
                .then(modifier)
        ) {
            MaintenanceToolsFormHeader(
                type = type,
                onAddButtonClicked = onAddButtonClicked,
                enabled = enabled
            )
            tools.forEachIndexed { index, tool ->
                Spacer(modifier = Modifier.height(14.dp))
                KrakatauOutlinedTextFieldWithLabel(
                    value = tool.description,
                    onValueChanged = { onDescriptionChanged(index, it) },
                    label = "Description",
                    enabled = enabled
                )
                Spacer(modifier = Modifier.height(10.dp))
                KrakatauOutlinedTextFieldWithLabel(
                    value = tool.quantity.toString(),
                    onValueChanged = {
                        val number = if (it.isNotEmpty()) it.toInt() else 0
                        onQuantityChanged(index, number)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = "Quantity",
                    enabled = enabled
                )
                Spacer(modifier = Modifier.height(10.dp))
                KrakatauOutlinedTextFieldWithLabel(
                    value = tool.unitOfMeasurement.toString(),
                    onValueChanged = {
                        val number = if (it.isNotEmpty()) it.toInt() else 0
                        onUoMChanged(index, number)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = "Unit of Measurement",
                    enabled = enabled
                )
                if (index != tools.lastIndex) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(thickness = 2.dp)
                }
            }
        }
    }
}

@Composable
fun MaintenanceToolsFormHeader(
    modifier: Modifier = Modifier,
    type: MaintenanceToolsFormType = MaintenanceToolsFormType.Technician,
    onAddButtonClicked: () -> Unit = {},
    enabled: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TOOLS/ALAT",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        if (type == MaintenanceToolsFormType.Technician) {
            IconButton(
                onClick = onAddButtonClicked,
                modifier = Modifier.offset(x = 16.dp),
                enabled = enabled
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_circle_outline_24),
                    contentDescription = "Add form",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMaintenanceToolsForm() {
    PreventiveMaintenanceTheme {
        Surface {
            val tools = (1..3).map {
                MaintenanceTools(
                    documentId = it.toString(),
                    description = "Obeng",
                    quantity = 1,
                    unitOfMeasurement = 1
                )
            }
//            MaintenanceToolsForm(
//                tools = tools,
//                modifier = Modifier.padding(16.dp)
//            )
        }
    }
}