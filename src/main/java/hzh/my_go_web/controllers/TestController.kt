package hzh.my_go_web.controllers

import hzh.my_go_web.websocket.WebSocket
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    private lateinit var webSocket: WebSocket

//    @GetMapping(path = ["test"])
//    fun test() {
//        webSocket.sendMessageTo("nihao", "100000")
//    }
}