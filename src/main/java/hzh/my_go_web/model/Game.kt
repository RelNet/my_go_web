package hzh.my_go_web.model

import java.io.Serializable

data class Game(var id: Int = 0, var black: Int = 0, var white: Int = 0,
                var winner: Int = 0): Serializable
