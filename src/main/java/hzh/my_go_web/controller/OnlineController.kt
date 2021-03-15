package hzh.my_go_web.controller

import com.alibaba.fastjson.JSONObject
import hzh.my_go_web.model.Status
import hzh.my_go_web.util.redis.OnlineUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OnlineController {
    @Autowired
    private lateinit var onlineUserService: OnlineUserService

    @PostMapping(path = ["online/keep"])
    fun keepOnline(id: Int, name: String) {
        onlineUserService.keepOnline(id, name)
    }

    @GetMapping(path = ["online/getusers"])
    fun getUsers(): JSONObject {
        val result = JSONObject()
        val users = onlineUserService.getOnlineUsers()
        result["status"] = Status.SUCCESS
        result["users"] = users
        return result
    }
}