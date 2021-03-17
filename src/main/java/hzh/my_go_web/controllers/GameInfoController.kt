package hzh.my_go_web.controllers

import com.alibaba.fastjson.JSONObject
import hzh.my_go_web.util.sql.service.GameInfoService
import hzh.my_go_web.websocket.WebSocket
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GameInfoController {
    @Autowired
    private lateinit var gameInfoService: GameInfoService

    @Autowired
    private lateinit var webSocket: WebSocket

    @PostMapping(path = ["gameinfo/addstep"])
    fun addStep(gameId: Int, to: Int, player: Int, stepNum: Int, location: String) {
        gameInfoService.addStep(gameId, player, stepNum, location)
        val msgJSON = JSONObject();
        {
            msgJSON["stepNum"] = stepNum
            msgJSON["location"] = location
            msgJSON["behavior"] = "add_step"
        }()
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
        gameInfoService.addStep(gameId, player, stepNum, location)
    }

    @PostMapping(path = ["gameinfo/deletestep"])
    fun deleteStep(stepNum: Int, to: Int) {
        val msgJSON = JSONObject();
        {
            msgJSON["stepNum"] = stepNum
            msgJSON["behavior"] = "delete_step"
        }()
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
    }

    @PostMapping(path = ["gameinfo/agreedeletestep"])
    fun agreeDeleteStep(gameId: Int, stepNum: Int, to: Int) {
        val msgJSON = JSONObject();
        {
            msgJSON["stepNum"] = stepNum
            msgJSON["behavior"] = "delete_step"
        }()
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
        gameInfoService.deleteStep(gameId, stepNum)
    }

    @PostMapping(path = ["gameinfo/refusedeletestep"])
    fun refuseDeleteStep(to: Int) {
        val msgJSON = JSONObject();
        {
            msgJSON["status"] = "refuse"
        }()
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
    }
}