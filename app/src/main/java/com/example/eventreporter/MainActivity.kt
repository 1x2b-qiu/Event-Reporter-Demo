package com.example.eventreporter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.eventreporter.reporter.EventReporter
import com.example.eventreporter.sender.ConsoleSender
import com.example.eventreporter.sender.NetworkSender
import com.example.eventreporter.sender.UiLogSender
import com.example.eventreporter.ui.screen.ReporterScreen
import com.example.eventreporter.ui.theme.EventReporterTheme

class MainActivity : ComponentActivity() {
    private val uiLogSender = UiLogSender()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 EventReporter，添加 UiLogSender，可扩展
        EventReporter.init(
            scope = lifecycleScope,
            senders = listOf(ConsoleSender(), NetworkSender(), uiLogSender)
        )

        enableEdgeToEdge()
        setContent {
            EventReporterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReporterScreen(
                        uiLogSender = uiLogSender,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

