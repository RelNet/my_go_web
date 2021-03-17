package hzh.my_go_web.util.sql.service

import hzh.my_go_web.model.Game
import hzh.my_go_web.util.sql.Hibernate
import org.springframework.stereotype.Service

@Service
class GameService {

    fun addGame(black: Int, white: Int): Int? {
        val game = Game(black = black, white = white)
        val hibernate = Hibernate<Game, Int>()
        return hibernate.insert(game)
    }

    fun getGame(id: Int): List<Game> {
        val hibernate = Hibernate<Any, Game>()
        val hql = "SELECT id, black, white FROM Game game WHERE black = $id OR white = $id"
        return hibernate.queryList(hql)
    }

    fun getColor(id: Int): Game {
        val hibernate = Hibernate<Any, Game>()
        val hql = "SELECT black, white FROM Game game WHERE id = $id"
        return hibernate.queryList(hql)[0]
    }

    fun updateWinner(id: Int, winner: Int) {
        val hibernate = Hibernate<Any, Any>()
        val hql = "UPDATE Game game SET winner = $winner WHERE id = $id"
        hibernate.update(hql)
    }
}