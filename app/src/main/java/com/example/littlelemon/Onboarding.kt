package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(navController: NavController, context: Context) {
  var firstName by remember { mutableStateOf("") }
  var lastName by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var message by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo),
      contentDescription = "App Logo",
      modifier = Modifier.size(140.dp)
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF495E57))
        .padding(40.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "Let's get to know you",
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight.Light
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text(
      text = "Personal information",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .align(Alignment.Start)
        .padding(bottom = 16.dp)
    )

    OutlinedTextField(
      value = firstName,
      onValueChange = { firstName = it },
      label = { Text("First name") },
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
      value = lastName,
      onValueChange = { lastName = it },
      label = { Text("Last name") },
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
      value = email,
      onValueChange = { email = it },
      label = { Text("Email") },
      modifier = Modifier.fillMaxWidth(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    Spacer(modifier = Modifier.weight(1f))

    Button(
      onClick = {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
          message = "Registration unsuccessful. Please enter all data."
        } else {
          val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
          with(sharedPreferences.edit()) {
            putString("firstName", firstName)
            putString("lastName", lastName)
            putString("email", email)
            apply()
          }
          message = "Registration successful!"
          // الانتقال إلى Home بعد التسجيل
          navController.navigate(Home.route) {
            popUpTo(Onboarding.route) { inclusive = true }
          }
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFF4CE14),
        contentColor = Color.Black
      ),
      shape = RoundedCornerShape(6.dp)
    ) {
      Text("Register", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (message.isNotEmpty()) {
      Text(
        text = message,
        color = if (message.startsWith("Registration successful")) Color(0xFF4CAF50) else Color.Red,
        fontWeight = FontWeight.Bold
      )
    }
  }
}


