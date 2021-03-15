package hzh.my_go_web.util.redis

import hzh.my_go_web.model.User
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.params.SetParams

@Service
class OnlineUserService {
    companion object {
        private val config = JedisPoolConfig()
        private val pool = {
            config.maxIdle = 8
            config.maxTotal = 18
            JedisPool(config, "127.0.0.1", 6379, 2000)
        }()

        fun closePool() {
            pool.close()
        }

        private fun getJedis(): Jedis = pool.resource

        private const val ONLINE_USER_KEY = "user_"
    }

    fun getOnlineUsers(): List<User> {
        val jedis = getJedis()
        val list = ArrayList<User>()
        jedis.use { jedis ->
            val set = jedis.keys("$ONLINE_USER_KEY*")
            for (item in set) {
                val tempPair = decode(jedis.get(item))
                list.add(User(id = tempPair.first, name = tempPair.second))
            }
        }
        return list
    }

    private fun decode(string: String): Pair<Int, String> {
        val list = string.split(' ')
        return Pair(list[0].toInt(), list[1])
    }

    fun keepOnline(id: Int, name: String) {
        val jedis = getJedis()
        val setParams = SetParams().ex(90)
        jedis.use { jedis ->
            val key = "$ONLINE_USER_KEY$id"
            if (jedis.exists(key)) jedis.del(key)
            jedis.set(key, "$id $name", setParams)
        }
    }
}