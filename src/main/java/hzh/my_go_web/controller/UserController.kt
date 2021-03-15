package hzh.my_go_web.controller

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import hzh.my_go_web.model.Status
import hzh.my_go_web.model.User
import hzh.my_go_web.util.redis.OnlineUserService
import hzh.my_go_web.util.sql.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping(path = ["user/signup"])
    fun signUp(name: String, password: String): JSONObject {
        val id = userService.addUser(name, password)
        val result = JSONObject()
        var status = Status.SUCCESS
        if (id == null) {
            status = Status.FAIL
            result["msg"] = "注册失败"
        }
        result["id"] = id
        result["status"] = status
        return result
    }

    @PostMapping(path = ["user/login"])
    fun login(id: Int, password: String): JSONObject {
        val _password = userService.getPassword(id)
        val result = JSONObject()
        var status = Status.SUCCESS
        if (_password != password) {
            status = Status.FAIL
            result["msg"] = "账户不存在或者密码错误"
        }
        result["status"] = status
        return result
    }

    @PostMapping(path = ["user/update"])
    fun update(id: Int, name: String, password: String): JSONObject {
        userService.updateUser(id, name, password)
        val result = JSONObject()
        result["status"] = Status.SUCCESS
        return result
    }

    @PostMapping(path = ["user/getname"])
    fun getName(id: Int): JSONObject {
        val result = JSONObject()
        val name = userService.getName(id)
        var status = Status.SUCCESS
        if (name == null) {
            status = Status.FAIL
            result["msg"] = "查询异常"
        }
        result["status"] = status
        result["name"] = name
        return result
    }
}