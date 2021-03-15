package hzh.my_go_web.model

import java.io.Serializable

data class User(var id: Int = 0, var name: String = "",
                var password: String = ""):Serializable