package hzh.my_go_web.websocket

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import javax.websocket.OnClose
import javax.websocket.OnError
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint


@Component
@ServerEndpoint(value = "/connectWebsocket/{userId}")
class WebSocket {
    companion object {
        private val clients = ConcurrentHashMap<String, WebSocket>()
    }

    private var session: Session? = null
    private var userId: String? = null

    // 建立连接
    @OnOpen
    fun onOpen(@PathParam("userId") userId: String, session: Session) {
        this.userId = userId
        this.session = session
        clients[userId] = this
        println("${userId}连接")
    }

    @OnError
    fun onError(session: Session?, error: Throwable) {
        println("服务端发生了错误:" + error.message)
    }

    @OnClose
    fun onClose() {
        clients.remove(userId)
        println("${userId}关闭连接")
    }

    // 给指定的用户发送消息
    fun sendMessageTo(message: String, userId: String) {
        clients[userId]!!.session!!.asyncRemote.sendText(message)
    }
}