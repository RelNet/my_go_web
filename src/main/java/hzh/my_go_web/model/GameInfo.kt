package hzh.my_go_web.model

import java.io.Serializable

/**
 * player 0为白，1为黑
 */
data class GameInfo(var gameId: Int = 0, var player: Int = -1,
                    var stepNum: Int = -1, var location: String = ""): Serializable
