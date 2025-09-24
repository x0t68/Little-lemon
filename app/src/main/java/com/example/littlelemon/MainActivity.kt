@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.data.local.MenuItemRoom
import com.example.littlelemon.MenuRepository
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // إنشاء قاعدة البيانات Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little_lemon_db"
        ).build()

        // إنشاء HttpClient
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }

        // إنشاء الريبو
        val repo = MenuRepository(db, client)

        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()

                val scope = rememberCoroutineScope()
                var menuItems by remember { mutableStateOf(emptyList<MenuItemRoom>()) }

                // تحميل البيانات من الشبكة وتخزينها محلياً ثم قراءتها
                LaunchedEffect(Unit) {
                    scope.launch {
                        try {
                            repo.fetchAndStoreMenu()
                        } catch (_: Exception) { // Changed e to _
                            // لو فشل التحميل من النت، نحاول قراءة أي بيانات محلية متاحة
                            // ممكن تضيف لوج أو رسالة لو حبيت
                        }
                        menuItems = repo.getLocalMenu()
                    }
                }

                // نمرر menuItems للـ NavigationComposable عشان HomeScreen يتلقى البيانات
                NavigationComposable(
                    navController = navController,
                    context = this,
                    menuItems = menuItems
                )
            }
        }
    }
}
