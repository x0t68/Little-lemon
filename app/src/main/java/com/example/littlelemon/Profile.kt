package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Personal information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, bottom = 20.dp)
        )

        // عرض المعلومات بشكل مرتب
        ProfileInfoField(label = "First Name", value = firstName)
        ProfileInfoField(label = "Last Name", value = lastName)
        ProfileInfoField(label = "Email", value = email)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Clear data
                sharedPreferences.edit().clear().apply()
                // Navigate back to Onboarding
                navController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14), // استخدام اللون الأصفر الخاص بالبراند
                contentColor = Color.Black
            )
        ) {
            Text("Log out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileInfoField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
