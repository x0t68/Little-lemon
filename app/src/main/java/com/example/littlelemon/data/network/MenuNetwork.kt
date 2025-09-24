package com.example.littlelemon.data.network

import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    val menu: List<NetworkMenuItem>
)

@Serializable
data class NetworkMenuItem(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)
