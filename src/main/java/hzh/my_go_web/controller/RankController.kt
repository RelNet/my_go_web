package hzh.my_go_web.controller

import com.alibaba.fastjson.JSONObject
import hzh.my_go_web.model.Status
import hzh.my_go_web.util.sql.service.RankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RankController {
    @Autowired
    private lateinit var rankService: RankService

    @PostMapping(path = ["rank/get"])
    fun get(id: Int): JSONObject {
        val point = rankService.getPoint(id)
        val result = JSONObject()
        var status = Status.SUCCESS
        if (point == null) {
            status = Status.FAIL
            result["msg"] = "查询异常"
        }
        result["status"] = status
        result["point"] = point
        return result
    }

    @GetMapping(path = ["rank/top50"])
    fun top50(): JSONObject {
        val list = rankService.getTop50()
        val result = JSONObject()
        val sList = ArrayList<HashMap<String, String>>()
        for (it in list) {
            val tempMap = HashMap<String, String>()
            tempMap["name"] = it.first
            tempMap["point"] = it.second.toString()
            sList.add(tempMap)
        }
        result["top50"] = sList
        return result
    }
}