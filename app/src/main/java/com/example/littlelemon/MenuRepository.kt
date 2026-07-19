package com.example.littlelemon

import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.data.local.MenuItemRoom
import com.example.littlelemon.data.network.MenuNetwork
import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class MenuRepository(
    db: AppDatabase,
    private val client: HttpClient
) {
    private val dao = db.menuItemDao()

    suspend fun fetchAndStoreMenu() {
        Log.d("LittleLemonDebug", "1. بدء محاولة تحميل البيانات من الرابط...")
        try {
            val response: HttpResponse = client.get(
                "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
            )
            
            Log.d("LittleLemonDebug", "2. حالة الاستجابة من السيرفر: ${response.status}")
            
            if (response.status == HttpStatusCode.OK) {
                val rawJson = response.bodyAsText()
                Log.d("LittleLemonDebug", "3. تم استلام JSON بنجاح. طول النص: ${rawJson.length}")
                
                // استخدام الـ Json المحول يدوياً للتأكد من عدم استهلاك الاستجابة مرتين
                val jsonParser = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                val menuNetwork = jsonParser.decodeFromString<com.example.littlelemon.data.network.MenuNetwork>(rawJson)
                
                Log.d("LittleLemonDebug", "4. تم تحويل JSON بنجاح. عدد العناصر: ${menuNetwork.menu.size}")

                val items = menuNetwork.menu.map { menuItem ->
                    MenuItemRoom(
                        id = menuItem.id,
                        title = menuItem.title,
                        description = menuItem.description,
                        price = menuItem.price,
                        image = menuItem.image,
                        category = menuItem.category
                    )
                }
                
                Log.d("LittleLemonDebug", "5. جاري مسح قاعدة البيانات القديمة وتخزين العناصر الجديدة...")
                dao.clearAll()
                dao.insertAll(items)
                Log.d("LittleLemonDebug", "6. اكتملت عملية التخزين في قاعدة البيانات بنجاح.")
            } else {
                Log.e("LittleLemonDebug", "خطأ: السيرفر استجاب بحالة غير ناجحة: ${response.status}")
            }
        } catch (e: Exception) {
            Log.e("LittleLemonDebug", "خطأ كارثي أثناء جلب البيانات: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getLocalMenu(): List<MenuItemRoom> = dao.getAll()
}
