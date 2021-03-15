package hzh.my_go_web.util.sql

import org.hibernate.*
import org.hibernate.cfg.Configuration

/**
 * E类型
 * 实体类型
 *
 * R类型
 * 返回类型
 */
class Hibernate<E, R> {

    companion object {
        private val factory: SessionFactory = Configuration().configure().buildSessionFactory()

        fun closeFactory() {
            factory.close()
        }

        fun newSession() = factory.openSession()

        fun newTransaction(session: Session) = session.beginTransaction()
    }

    // 查询单个数据
    fun query(hql: String): R? {
        val session = newSession()
        val transaction = newTransaction(session)
        var result: R? = null
        try {
            val query = session.createQuery(hql)
            val list = query.list()

            if (list.isEmpty()) return null;

            result = list[0] as R
            transaction.commit()
        } catch (e: HibernateException) {
            transaction?.rollback()
            e.printStackTrace()
        } finally {
            session.close()
        }
        return result
    }

    fun queryList(hql: String): List<R> {
        val session = newSession()
        val transaction = newTransaction(session)
        val result = ArrayList<R>()
        try {
            val query = session.createQuery(hql)
            val list = query.list()
            for (e in list) {
                result.add(e as R)
            }
            transaction.commit()
        } catch (e: HibernateException) {
            transaction?.rollback()
            e.printStackTrace()
        } finally {
            session.close()
        }
        return result
    }

    fun sqlQueryList(sql: String): List<*> {
        val session = newSession()
        val transaction = newTransaction(session)
        var result: List<*> = ArrayList<Any>()
        try {
            val query: SQLQuery<*> = session.createSQLQuery(sql)
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
            result = query.list()
            transaction.commit()
        } catch (e: HibernateException) {
            transaction?.rollback()
            e.printStackTrace()
        } finally {
            session.close()
        }
        return result
    }

    fun insert(entity: E): R? {
        val session = newSession()
        val transaction = newTransaction(session)
        var result: R? = null
        try {
            result = session.save(entity) as R
            transaction.commit()
        } catch (e: HibernateException) {
            transaction?.rollback()
            e.printStackTrace()
        } finally {
            session.close()
        }
        return result
    }

    fun update(hql: String) {
        val session = newSession()
        val transaction = newTransaction(session)
        try {
            val query = session.createQuery(hql)
            query.executeUpdate()
            transaction.commit()
        } catch (e: HibernateException) {
            transaction?.rollback()
            e.printStackTrace()
        } finally {
            session.close()
        }
    }

    fun delete(hql: String) {
        update(hql)
    }
}