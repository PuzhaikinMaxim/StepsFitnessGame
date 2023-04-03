package com.puj.stepsfitnessgame.data.network.inventoryitem

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response

class InventoryItemDataProviderImpl(
    private val token: String
): InventoryItemDataProvider {

    private val service = ServiceFactory.create(InventoryItemApiService::class.java)

    override suspend fun equipItem(inventoryId: Int, slot: Int): Response<Unit> {
        try {
            val response = service.equipItem(token, ItemEquipDataDto(inventoryId, slot))
            if(response.isSuccessful){
                if(response.body() != null){
                    return Response.Success(Unit)
                }
                return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun unequipItem(slot: Int): Response<Unit> {
        try {
            val response = service.unequipItem(token, slot)
            if(response.isSuccessful){
                if(response.body() != null){
                    return Response.Success(Unit)
                }
                return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun getInventoryItemList(): Response<List<InventoryItemDto>> {
        try {
            val response = service.getInventoryItems(token)
            if(response.isSuccessful){
                if(response.body() != null){
                    return Response.Success(response.body() ?: listOf())
                }
                return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }
}