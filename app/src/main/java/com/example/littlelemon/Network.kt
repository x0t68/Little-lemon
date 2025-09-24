@file:OptIn(InternalSerializationApi::class) // Removed redundant qualifier
package com.example.littlelemon

import com.example.littlelemon.data.local.MenuItemRoom
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MenuNetwork(

    @SerialName("menu")
    val items: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: Double,

    @SerialName("category")
    val category: String,

    @SerialName("image")
    val imageUrl: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        price = price.toString(), // Converted Double to String
        description = description,
        category = category,
        image = imageUrl // Changed parameter name to 'image'
    )
}