package com.example.littlelemon

import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.data.local.MenuItemRoom
import com.example.littlelemon.data.network.MenuNetwork
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class MenuRepository(
    db: AppDatabase, // Removed 'private val'
    private val client: HttpClient
) {
    private val dao = db.menuItemDao()

    suspend fun fetchAndStoreMenu() {
        val response: HttpResponse = client.get(
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        ) { contentType(ContentType.Application.Json) }

        // Explicitly specify the type for body()
        val menuNetwork: MenuNetwork = response.body<MenuNetwork>()

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

        dao.clearAll()
        dao.insertAll(items)
    }

    suspend fun getLocalMenu(): List<MenuItemRoom> = dao.getAll()
}
