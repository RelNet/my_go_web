package hzh.my_go_web.util.sql.service

import hzh.my_go_web.model.GameInfo
import hzh.my_go_web.util.sql.Hibernate

class GameInfoService {

    fun addStep(gameId: Int, player: Int, stepNum: Int, location: String) {
        val hibernate = Hibernate<GameInfo, Any>()
        val gameInfo = GameInfo(gameId, player, stepNum, location)
        hibernate.insert(gameInfo)
    }

    fun deleteStep(gameId: Int, stepNum: Int) {
        val hql = "DELETE FROM GameInfo game_info WHERE game_id = $gameId AND step_num = $stepNum"
        val hibernate = Hibernate<Any, Any>()
        hibernate.delete(hql)
    }
}