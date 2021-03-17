package hzh.my_go_web.util.game

import hzh.my_go_web.util.sql.service.GameInfoService
import hzh.my_go_web.util.sql.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Scorer {
    @Autowired
    private lateinit var gameService: GameService

    @Autowired
    private lateinit var gameInfoService: GameInfoService
    /*
    // 返回胜利者
    fun score(gameId: Int): Int {
        val players = gameService.getColor(gameId)
        val steps = gameInfoService.getAllStep(gameId)

    }
     */
}