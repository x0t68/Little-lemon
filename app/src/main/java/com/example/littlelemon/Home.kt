package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.local.MenuItemRoom

// Define colors for easy reuse and consistency
val YellowPrimary = Color(0xFFF4CE14)
val GreenPrimary = Color(0xFF495E57)
val LightGrayBackground = Color(0xFFEDEFEE)
val DarkGrayText = Color(0xFF333333)
val MediumGrayText = Color.Gray
val WhiteBackground = Color.White

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, menuItems: List<MenuItemRoom>) {
    var searchPhrase by remember { mutableStateOf("") }
    val allCategories = menuItems.map { it.category.replaceFirstChar { char -> char.uppercaseChar() } }.distinct().sorted()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val filteredMenuItems = remember(menuItems, searchPhrase, selectedCategory) {
        menuItems.filter {
            val matchesSearch = if (searchPhrase.isBlank()) {
                true
            } else {
                it.title.contains(searchPhrase, ignoreCase = true) ||
                it.description.contains(searchPhrase, ignoreCase = true)
            }
            val matchesCategory = if (selectedCategory == null) {
                true
            } else {
                it.category.equals(selectedCategory, ignoreCase = true)
            }
            matchesSearch && matchesCategory
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Bar with Logo and Profile
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.height(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }

        // Hero Section
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = GreenPrimary
        ) {
            Column( // Main column for Hero content + Search Bar
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top=16.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top // Align top for text and image
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp)
                    ) {
                        Text(
                            text = "Little Lemon",
                            style = MaterialTheme.typography.displaySmall,
                            color = YellowPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Chicago",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            lineHeight = 20.sp,
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(width = 120.dp, height = 140.dp)

                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp)) // Space before search bar
                OutlinedTextField(
                    value = searchPhrase,
                    onValueChange = { searchPhrase = it },
                    placeholder = { Text("Enter search phrase", color = MediumGrayText) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon", tint = DarkGrayText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = WhiteBackground, // White background for the TextField
                        focusedBorderColor = Color.Transparent, // No border when focused
                        unfocusedBorderColor = Color.Transparent, // No border when unfocused
                        cursorColor = GreenPrimary,
                        focusedTextColor = DarkGrayText,
                        unfocusedTextColor = DarkGrayText
                    )
                )
            }
        }

        // Content below Hero Section
        Column(modifier = Modifier.padding(horizontal = 16.dp)){
            Text(
                text = "ORDER FOR DELIVERY",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val categories = listOf("Starters", "Mains", "Desserts", "Drinks")

                categories.forEach { category ->
                    Button(
                        onClick = {
                            selectedCategory = if (selectedCategory == category) null else category
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(category)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                allCategories.forEach { category ->
                    val isSelected = category.equals(selectedCategory, ignoreCase = true)
                    Button(
                        onClick = {
                            selectedCategory = if (isSelected) null else category
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) GreenPrimary else LightGrayBackground,
                            contentColor = if (isSelected) YellowPrimary else DarkGrayText
                        ),
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        Text(category, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Menu items list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp) // Adjusted padding
        ) {
            itemsIndexed(filteredMenuItems) { index, item ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).padding(end = 12.dp)
                        ) {
                            Text(
                                item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = DarkGrayText
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                item.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MediumGrayText,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "$${item.price}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = DarkGrayText
                            )
                        }
                        GlideImage(
                            model = item.image,
                            contentDescription = item.title,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    if (index < filteredMenuItems.size - 1) {
                        HorizontalDivider(thickness = 1.dp, color = LightGrayBackground, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }
}

// Ensure R.drawable.hero_image exists.
// The Profile object was removed from here as it's defined in Destinations.kt
