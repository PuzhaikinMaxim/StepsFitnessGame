package com.puj.stepsfitnessgame.data.network.duel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

class DuelStompClient(private val token: String) {

    private val gson: Gson = GsonBuilder().create()
    private var stompClient: StompClient? = null

    private var topicSubscribe: Disposable? = null
    private var lifecycleSubscribe: Disposable? = null

    private val _isOpponentFound = MutableLiveData(false)
    val isOpponentFound: LiveData<Boolean>
        get() = _isOpponentFound

    init {
        stompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            SOCKET_URL
        ).withClientHeartbeat(3000)

        /*
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

         */
    }

    fun tryFindGame() {
        stompClient!!.send(METHOD_ADDRESS).subscribe({
            println("Success")
        },
            {
                it.printStackTrace()
            })
    }

    fun initializeConnection(username: String) {
        if (stompClient != null) {
            println("-------------------------------------------")
            println(username)
            topicSubscribe = stompClient!!.topic("$TOPIC/$username")
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage: StompMessage ->
                    val opponentFound = topicMessage.payload.toBoolean()
                    _isOpponentFound.postValue(opponentFound)
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
                val stompHeader = StompHeader("token",token)
                stompClient!!.connect(listOf(stompHeader))
            }


        } else {
            println("stompClient is null!")
        }
    }

    fun stopDuelSearch() {
        topicSubscribe?.dispose()
        lifecycleSubscribe?.dispose()
        stompClient?.disconnect()
    }

    companion object {
        const val SOCKET_URL = "http://192.168.1.195:8080/ws"
        const val METHOD_ADDRESS = "/api/try_find_opponent"
        const val TOPIC = "/topic/duel"
    }
}