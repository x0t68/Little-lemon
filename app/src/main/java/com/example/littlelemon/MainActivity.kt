@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import android.os.Bundle
import android.util.Log
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
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little_lemon_db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // إنشاء الريبو
        val repo = MenuRepository(db, client)

        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                var menuItems by remember { mutableStateOf(emptyList<MenuItemRoom>()) }

                // تحميل البيانات من الشبكة وتخزينها محلياً ثم قراءتها
                LaunchedEffect(Unit) {
                    Log.d("LittleLemonDebug", "A. دخلنا الـ LaunchedEffect في MainActivity")
                    try {
                        repo.fetchAndStoreMenu()
                        Log.d("LittleLemonDebug", "B. تم استدعاء fetchAndStoreMenu بنجاح")
                    } catch (e: Exception) {
                        Log.e("LittleLemonDebug", "C. حدث خطأ في MainActivity: ${e.message}")
                    }
                    
                    val localItems = repo.getLocalMenu()
                    Log.d("LittleLemonDebug", "D. عدد العناصر المسترجعة من قاعدة البيانات المحلية: ${localItems.size}")
                    menuItems = localItems
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
