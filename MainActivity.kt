package com.example.linisnasugbu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linisnasugbu.ui.theme.LoginScreen
import com.example.linisnasugbu.ui.theme.LinisNasugbuTheme

private val EmeraldPrimary = Color(0xFF059669)
private val EmeraldDark = Color(0xFF047857)
private val EmeraldLight = Color(0xFFA7F3D0)
private val BackgroundGray = Color(0xFFF2F2F7)
private val RoseWarning = Color(0xFFF43F5E)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinisNasugbuTheme {
                LinisNasugbuAppInterface()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinisNasugbuAppInterface() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentRole by remember { mutableStateOf("Resident") }
    var currentScreen by remember { mutableStateOf("home") }
    var showRequestModal by remember { mutableStateOf(false) }
    var showReportModal by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(onLoginSuccess = { isLoggedIn = true })
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Linis Nasugbu ($currentRole)",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldPrimary),
                    actions = {
                        TextButton(onClick = {
                            currentRole = when (currentRole) {
                                "Resident" -> "Collector"
                                "Collector" -> "Admin"
                                else -> "Resident"
                            }
                        }) {
                            Text("Switch Role", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            },
            bottomBar = {
                if (currentRole == "Resident") {
                    NavigationBar(containerColor = Color.White) {
                        NavigationBarItem(
                            selected = currentScreen == "home",
                            onClick = { currentScreen = "home" },
                            icon = { Text("🏠", fontSize = 18.sp) },
                            label = { Text("Home") }
                        )
                        NavigationBarItem(
                            selected = currentScreen == "map",
                            onClick = { currentScreen = "map" },
                            icon = { Text("📍", fontSize = 18.sp) },
                            label = { Text("Tracker") }
                        )
                        NavigationBarItem(
                            selected = currentScreen == "bookings",
                            onClick = { currentScreen = "bookings" },
                            icon = { Text("📅", fontSize = 18.sp) },
                            label = { Text("Schedule") }
                        )
                        NavigationBarItem(
                            selected = currentScreen == "profile",
                            onClick = { currentScreen = "profile" },
                            icon = { Text("👤", fontSize = 18.sp) },
                            label = { Text("Profile") }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentRole) {
                    "Collector" -> CollectorScreen()
                    "Admin" -> AdminScreen()
                    else -> {
                        when (currentScreen) {
                            "home" -> HomeScreen(
                                userName = "Juan Dela Cruz",
                                userAddress = "123 Mabini St., Brgy. 4",
                                userZone = "Zone 1 - Route B",
                                onNavigateToMap = { currentScreen = "map" },
                                onOpenRequestModal = { showRequestModal = true },
                                onOpenReportModal = { showReportModal = true }
                            )
                            "map" -> MapTrackerScreen(onBack = { currentScreen = "home" })
                            "bookings" -> BookingsScreen()
                            "profile" -> ProfileScreen(onLogout = { isLoggedIn = false })
                        }
                    }
                }
            }

            if (showRequestModal) {
                RequestBulkModal(onDismiss = { showRequestModal = false })
            }
            if (showReportModal) {
                ReportMissedModal(onDismiss = { showReportModal = false })
            }
        }
    }
}

@Composable
fun HomeScreen(
    userName: String,
    userAddress: String,
    userZone: String,
    onNavigateToMap: () -> Unit,
    onOpenRequestModal: () -> Unit,
    onOpenReportModal: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color.White,
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "JD",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = EmeraldPrimary,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Mabuhay, ${userName.split(' ').firstOrNull() ?: "Juan"}! 👋",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    fontSize = 17.sp
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = EmeraldLight,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = userAddress,
                                        fontSize = 11.sp,
                                        color = EmeraldLight,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        color = Color.Black.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = EmeraldLight,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Friday, Nov 06, 2026",
                                    fontSize = 11.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Surface(color = EmeraldLight, shape = CircleShape) {
                                Text(
                                    text = userZone.split('-').firstOrNull() ?: "Route B",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = EmeraldDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "NEXT PICKUP ALERT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Surface(color = Color(0xFFFEF3C7), shape = CircleShape) {
                            Text(
                                text = "Truck Nearby",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "TODAY • 10:30 AM - 12:00 PM",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "🟢 Biodegradable (Wet Waste)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = EmeraldDark
                            )
                            Text(
                                text = "Status: 3 streets away (En Route)",
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "WHAT WOULD YOU LIKE TO DO?",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickCardItem(
                    "Track Truck",
                    "Live Map & ETA",
                    Icons.Default.Place,
                    EmeraldPrimary,
                    Modifier.weight(1f),
                    onClick = onNavigateToMap
                )
                QuickCardItem(
                    "Request Bulk",
                    "Special Waste",
                    Icons.Default.ShoppingCart,
                    Color(0xFF0D9488),
                    Modifier.weight(1f),
                    onClick = onOpenRequestModal
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickCardItem(
                    "Schedule",
                    "Regular Pickup",
                    Icons.Default.DateRange,
                    EmeraldPrimary,
                    Modifier.weight(1f),
                    onClick = { }
                )
                QuickCardItem(
                    "Report Issue",
                    "Missed Pickup",
                    Icons.Default.Warning,
                    RoseWarning,
                    Modifier.weight(1f),
                    onClick = onOpenReportModal
                )
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GARBAGE SORTING GUIDE TODAY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        WasteGuideBadge(
                            "🟢 Bio / Wet",
                            Color(0xFFDCFCE7),
                            Color(0xFF166534),
                            Modifier.weight(1f)
                        )
                        WasteGuideBadge(
                            "🔵 Recyclable",
                            Color(0xFFE0F2FE),
                            Color(0xFF075985),
                            Modifier.weight(1f)
                        )
                        WasteGuideBadge(
                            "🔴 Hazardous",
                            Color(0xFFFFE4E6),
                            Color(0xFF9F1239),
                            Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickCardItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = iconColor.copy(alpha = 0.15f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(subtitle, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun WasteGuideBadge(title: String, bgColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
fun MapTrackerScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📍 Truck Live Map Tracker", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(8.dp))
        Text("En Route: Truck #04 - Zone 1", fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("🗺️ [ Map View Simulation ]", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun BookingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp)
    ) {
        Text("📅 Pickup Schedule & History", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Regular Waste Collection", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Every Monday & Friday • 8:00 AM", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Surface(
            shape = CircleShape,
            color = EmeraldPrimary,
            modifier = Modifier.size(70.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("👤", fontSize = 32.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Juan Dela Cruz", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Resident • Zone 1", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = RoseWarning),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogout() }
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(14.dp)) {
                Text("LOG OUT", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun RequestBulkModal(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Request Bulk Pickup", fontWeight = FontWeight.Bold) },
        text = { Text("Mag-request ng special pickup para sa malalaking basura tulad ng kahoy o lumang muwebles.") },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Text("Submit Request")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ReportMissedModal(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Report Missed Pickup", fontWeight = FontWeight.Bold) },
        text = { Text("Mag-iwan ng ulat kung hindi nakuha ang basura sa iyong nakatakdang araw at oras.") },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Text("Submit Report")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CollectorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🚛 Collector / Driver Dashboard", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Route: Zone 1 - Sector B", fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Collection Progress", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.6f },
                    color = EmeraldPrimary,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("12 of 20 Houses Picked Up", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun AdminScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📊 Admin / Manager View", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Nasugbu Municipal Waste Management", fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Active Trucks", fontSize = 12.sp, color = Color.Gray)
                    Text("4 / 5", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                }
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Pending Requests", fontSize = 12.sp, color = Color.Gray)
                    Text("8 Requests", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                }
            }
        }
    }
}