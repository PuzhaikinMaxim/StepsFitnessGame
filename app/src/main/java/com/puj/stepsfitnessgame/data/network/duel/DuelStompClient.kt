package com.puj.stepsfitnessgame.data.network.duel

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage

class DuelStompClient {

    private val gson: Gson = GsonBuilder().create()
    private var stompClient: StompClient? = null

    private var topicSubscribe: Disposable? = null
    private var lifecycleSubscribe: Disposable? = null

    init {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL).withClientHeartbeat(3000)
        println(stompClient)
        CoroutineScope(Dispatchers.Default).launch{
            for(i in 0..100) {
                delay(1000)
                stompClient!!.send(METHOD_ADDRESS).subscribe({
                    println("Success")
                },
                    {
                        it.printStackTrace()
                    })
            }
            topicSubscribe?.dispose()
            lifecycleSubscribe?.dispose()
        }

        testConnection()
    }

    fun testConnection() {
        if (stompClient != null) {
            topicSubscribe = stompClient!!.topic(TOPIC)
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage: StompMessage ->
                    println(topicMessage.payload)
                },
                {
                    it.printStackTrace()
                }
                )

            lifecycleSubscribe = stompClient!!.lifecycle()
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent: LifecycleEvent ->
                    when (lifecycleEvent.type!!) {
                        LifecycleEvent.Type.OPENED -> println("Stomp connection opened")
                        LifecycleEvent.Type.ERROR -> println("Error")
                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT,
                        LifecycleEvent.Type.CLOSED -> {
                            println("Stomp connection closed")
                        }
                    }
                }

            if (!stompClient!!.isConnected) {
                val stompHeader = StompHeader("token","userf72afb76-8230-4587-ba04-f6c6639d8538")
                stompClient!!.connect(listOf(stompHeader))
            }


        } else {
            println("stompClient is null!")
        }
    }

    companion object {
        const val SOCKET_URL = "http://192.168.1.195:8080/ws"
        const val METHOD_ADDRESS = "/api/sock_test"
        const val TOPIC = "/topic/test"
    }
}