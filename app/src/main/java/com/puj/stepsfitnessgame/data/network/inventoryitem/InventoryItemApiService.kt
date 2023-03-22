package com.puj.stepsfitnessgame.data.network.inventoryitem

import retrofit2.Response
import retrofit2.http.*

interface InventoryItemApiService {

    @PUT("player/equip_item")
    suspend fun equipItem(
        @Header("token") token: String,
        @Body itemEquipDataDto: ItemEquipDataDto,
    ): Response<String>

    @PUT("player/un_equip_item/{slot}")
    suspend fun unequipItem(@Header("token") token: String, @Path("slot") slot: Int): Response<String>

    @GET("player/get_inventory_items")
    suspend fun getInventoryItems(@Header("token") token: String): Response<List<InventoryItemDto>>
}