package hzh.my_go_web.controllers

import com.alibaba.fastjson.JSONObject
import hzh.my_go_web.model.Game
import hzh.my_go_web.util.game.Scorer
import hzh.my_go_web.util.sql.service.GameService
import hzh.my_go_web.websocket.WebSocket
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicBoolean

@RestController
class GameController {
    companion object {
        // true说明ai被占用
        val aiLock = AtomicBoolean(false)
        const val AI_ID = 100_000
    }

    @Autowired
    private lateinit var gameService: GameService

    @Autowired
    private lateinit var webSocket: WebSocket

    @Autowired
    private lateinit var scorer: Scorer

    @PostMapping(path = ["game/addgame"])
    fun addGame(sponsor: Int, inviter: Int, divideFirst: Boolean, black: Int,
                white: Int, giver: Boolean, giverColor: Boolean, giverNum: Int) {

        if (inviter == AI_ID) {
            val msgJSON = JSONObject()
            if (aiLock.get()) {
                msgJSON["behavior"] = "refuse"
                webSocket.sendMessageTo(msgJSON.toString(), sponsor.toString())
                return
            }

            aiLock.set(true)
            msgJSON["behavior"] = "agree"
            return
        }
        val msgJSON = JSONObject();
        {
            var _black: Int = 0
            var _white: Int = 0
            if (!divideFirst) {
                val random = (Math.random() - Math.random()).toInt()
                if (random > 0) {
                    _black = sponsor
                    _white = inviter
                } else {
                    _black = inviter
                    _white = sponsor
                }
            }
            msgJSON["sponsor"] = sponsor
            msgJSON["inviter"] = inviter
            msgJSON["divideFirst"] = divideFirst
            msgJSON["black"] = _black
            msgJSON["white"] = _white
            msgJSON["giver"] = giver
            msgJSON["giverColor"] = giverColor
            msgJSON["giverNum"] = giverNum
            msgJSON["behavior"] = "invite"
        }()
        webSocket.sendMessageTo(msgJSON.toString(), inviter.toString())
    }

    @PostMapping(path = ["game/agreeaddgame"])
    fun agreeAddGame(sponsor: Int, inviter: Int, black: Int, white: Int): Int {
        val id = gameService.addGame(black, white)
        val msgJSON = JSONObject();
        {
            msgJSON["black"] = black
            msgJSON["white"] = white
            msgJSON["gameId"] = id
            msgJSON["behavior"] = "agree"
        }()
        webSocket.sendMessageTo(msgJSON.toString(), sponsor.toString())
        return id!!
    }

    @PostMapping(path = ["game/refuseaddgame"])
    fun refuse(sponsor: Int) {
        val msgJSON = JSONObject()
        msgJSON["behavior"] = "refuse"
        webSocket.sendMessageTo(msgJSON.toString(), sponsor.toString())
    }

    // 获取用户的所有对战信息
    @GetMapping(path = ["game/getgames"])
    fun getGames(userId: Int): List<Game> = gameService.getGame(userId)

    /*
    记分相关
    @PostMapping(path = ["game/score"])
    fun score(to: Int) {
        val msgJSON = JSONObject()
        msgJSON["behavior"] = "score"
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
    }

    @PostMapping(path = ["game/agreescore"])
    fun agreeScore(gameId: Int, to: Int): Int {
        val msgJSON = JSONObject()
        msgJSON["behavior"] = "agree"
        val winner = scorer.score(gameId)
        msgJSON["winner"] = winner
        gameService.updateWinner(gameId, winner)
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
        return winner
    }

    @PostMapping(path = ["game/refusescore"])
    fun refuseScore(to: Int) {
        val msgJSON = JSONObject()
        msgJSON["behavior"] = "refuse"
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
    }
     */

    @PostMapping(path = ["game/surrender"])
    fun surrender(gameId: Int, to: Int) {
        val msgJSON = JSONObject()
        msgJSON["behavior"] = "surrender"
        webSocket.sendMessageTo(msgJSON.toString(), to.toString())
        gameService.updateWinner(gameId, to)
    }
}