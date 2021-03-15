package hzh.my_go_web.util.sql.service

import hzh.my_go_web.model.User
import hzh.my_go_web.util.sql.Hibernate
import org.springframework.stereotype.Service

@Service
class UserService {

    fun addUser(name: String, password: String): Int? {
        val hibernate = Hibernate<User, Int>()
        val user = User(name = name, password = password)
        val id = hibernate.insert(user)

        val service = RankService()
        if (id != null) service.addRanker(id)

        return id
    }

    fun getPassword(id: Int): String? {
        val hql = "SELECT password FROM User user WHERE id = $id"
        val hibernate = Hibernate<Any, String>()
        return hibernate.query(hql);
    }

    fun getName(id: Int): String? {
        val hql = "SELECT name FROM User user WHERE id = $id"
        val hibernate = Hibernate<Any, String>()
        return hibernate.query(hql)
    }

    fun updateUser(id: Int, name: String, password: String) {
        val hql = "UPDATE User user SET name = '$name', password = '$password' WHERE id = $id"
        val hibernate = Hibernate<Any, Any>()
        hibernate.update(hql)
    }
}