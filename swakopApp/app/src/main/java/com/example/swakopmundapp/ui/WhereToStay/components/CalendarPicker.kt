package com.example.swakopmundapp.ui.wheretostay.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarPicker(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentMonth by remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }

    Column {
        OutlinedTextField(
            value = selectedDate?.toString() ?: "",
            onValueChange = {},
            label = { Text(label) },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        if (expanded) {
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        IconButton(onClick = {
                            currentMonth = currentMonth.minusMonths(1)
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
                        }
                        Text("${currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${currentMonth.year}")
                        IconButton(onClick = {
                            currentMonth = currentMonth.plusMonths(1)
                        }) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    val days = buildCalendar(currentMonth)
                    days.chunked(7).forEach { week ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            week.forEach { date ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clickable {
                                            onDateSelected(date)
                                            expanded = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Surface(
                                        shape = MaterialTheme.shapes.small,
                                        color = if (date == selectedDate) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        tonalElevation = if (date == selectedDate) 4.dp else 0.dp
                                    ) {
                                        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                                            Text(
                                                text = date.dayOfMonth.toString(),
                                                fontWeight = if (date == selectedDate) FontWeight.Bold else FontWeight.Normal,
                                                color = if (date == selectedDate)
                                                    MaterialTheme.colorScheme.onPrimary
                                                else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun buildCalendar(startOfMonth: LocalDate): List<LocalDate> {
    val start = startOfMonth.with(DayOfWeek.SUNDAY)
        .minusDays(startOfMonth.withDayOfMonth(1).dayOfWeek.value % 7L)
    return List(42) { start.plusDays(it.toLong()) }
}
