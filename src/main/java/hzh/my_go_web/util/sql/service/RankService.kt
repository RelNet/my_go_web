package hzh.my_go_web.util.sql.service

import hzh.my_go_web.model.Rank
import hzh.my_go_web.util.sql.Hibernate
import org.springframework.stereotype.Service

@Service
class RankService {

    fun addRanker(id: Int) {
        val rank = Rank(id = id)
        val hibernate = Hibernate<Rank, Any>()
        hibernate.insert(rank)
    }

    fun updatePoint(id: Int, point: Int) {
        val hql = "UPDATE Rank rank SET point = $point WHERE id = $id"
        val hibernate = Hibernate<Any, Any>()
        hibernate.update(hql)
    }

    fun getPoint(id: Int): Int? {
        val hibernate = Hibernate<Any, Int>()
        val hql = "SELECT point FROM Rank rank WHERE id = $id"
        return hibernate.query(hql)
    }

    /**
     * 第一个是用户名
     * 第二个是分数
     */
    fun getTop50(): List<Pair<String, Int>> {
        val sql = "SELECT \"user\".name, point FROM go.rank " +
                "INNER JOIN go.user ON \"user\".id = rank.id " +
                "ORDER BY point DESC LIMIT 50"
        val hibernate = Hibernate<Any, Any?>()
        val tempList = hibernate.sqlQueryList(sql)
        val list = ArrayList<Pair<String, Int>>()

        for (item in tempList) {
            val row = item as Map<*, *>
            list.add(Pair(row["name"] as String, row["point"] as Int))
        }
        return list
    }
}