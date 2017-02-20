/**
 * 数据库访问公共方法
 * 最近更新：2015-05-08，yangdh
 */
package cn.careerforce.dao;

import cn.careerforce.util.StrUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("rawtypes")
@Component
public class BaseDao
{
    @PersistenceContext
    private EntityManager entityManager;


    public Object getObjectById(Class<?> cls, Serializable id)
    {
        return entityManager.unwrap(Session.class).get(cls, id);
    }

    public void saveObject(Object obj)
    {
        entityManager.unwrap(Session.class).save(obj);
    }


    public void deleteObject(Object obj)
    {
        entityManager.unwrap(Session.class).delete(obj);
    }


    private List<?> getAllObjects(final String sql, final Object[] params, boolean transformMap)
    {
        Session session = entityManager.unwrap(Session.class);
        Query query;
        if (transformMap)
            query = session.createQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        else
            query = session.createQuery(sql);
        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        return query.list();
    }

    public List<?> getAllObjects(final String sql, final Object[] params)
    {
        return getAllObjects(sql, params, false);
    }

    public List<?> getAllMappedObjects(final String sql, final Object[] params)
    {
        return getAllObjects(sql, params, true);
    }

    public List<?> getObjectsByLimit(final String sql, final Object[] params, final int start, final int pagesize)
    {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(sql);
        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        if (start != -1 && pagesize != -1)
        {
            query.setFirstResult(start);
            query.setMaxResults(pagesize);
        }
        return query.list();
    }


    public List<?> getObjectsByPage(final String sql, final Object[] params, final int page, final int pagesize)
    {
        return getObjectsByPage(sql, params, page, pagesize, false);
    }


    public List<?> getMappedObjectsByPage(final String sql, final Object[] params, final int page, final int pagesize)
    {
        return getObjectsByPage(sql, params, page, pagesize, true);
    }


    private List<?> getObjectsByPage(final String sql, final Object[] params, final int page, final int pagesize, boolean transformMap)
    {
        Session session = entityManager.unwrap(Session.class);
        Query query;
        if (transformMap)
            query = session.createQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        else
            query = session.createQuery(sql);
        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        if (page != -1 && pagesize != -1)
        {
            query.setFirstResult((page - 1) * pagesize);
            query.setMaxResults(pagesize);
        }
        return query.list();
    }


    public List<?> getAllObjectsBySql(final String sql, final Object[] params)
    {
        return getAllObjectsBySql(sql, params, null);
    }

    public List<?> getAllObjectsBySql(final String sql, final Object[] params, Class<?> entity)
    {
        return getObjectsBySqlByPage(sql, params, entity, 1, -1);
    }


    public List<?> getObjectsBySqlByPage(final String sql, final Object[] params, final int page, final int pagesize)
    {
        return this.getObjectsBySqlByPage(sql, params, null, page, pagesize);
    }

    public List<?> getObjectsBySqlByPage(final String sql, final Object[] params, final Class<?> entity, final int page, final int pagesize)
    {
        return this.getObjectsBySqlByPage(sql, params, entity, page, pagesize, false, false);
    }

    public List<?> getObjectsMapBySqlByPage(final String sql, final Object[] params, final int page, final int pagesize)
    {
        return this.getObjectsBySqlByPage(sql, params, null, page, pagesize, false, true);
    }

    public List<?> getObjectsBySqlByPage(final String sql, final Object[] params, final Class<?> entity, final int page, final int pagesize, boolean transformResult, boolean transformMap)
    {
        Session session = entityManager.unwrap(Session.class);
        Query query;
        if (entity != null)
        {
            if (transformResult)
                query = session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(entity));
            else
                query = session.createSQLQuery(sql).addEntity(entity);
        }
        else if (transformMap)
            query = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        else
            query = session.createSQLQuery(sql);

        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        if (page != -1 && pagesize != -1)
        {
            query.setFirstResult((page - 1) * pagesize);
            query.setMaxResults(pagesize);
        }
        return query.list();
    }

    public List<?> getObjectsBySqlByPage(final String sql, final Object[] params, final String[] eItems, final Class[] entitys, final int page, final int pagesize)
    {
        Session session = entityManager.unwrap(Session.class);
        Query query;
        if (entitys.length == 2)
            query = session.createSQLQuery(sql).addEntity(eItems[0], entitys[0]).addEntity(eItems[1], entitys[1]);
        else if (entitys.length == 3)
            query = session.createSQLQuery(sql).addEntity(eItems[0], entitys[0]).addEntity(eItems[1], entitys[1]).addEntity(eItems[2], entitys[2]);
        else
            query = session.createSQLQuery(sql);

        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        if (page != -1 && pagesize != -1)
        {
            query.setFirstResult((page - 1) * pagesize);
            query.setMaxResults(pagesize);
        }
        return query.list();
    }

    public List<?> getObjectsBySqlWithEntitiesByPage(final String sql, final Object[] params, final List<Object> entities, final int page, final int pagesize)
    {
        Session session = entityManager.unwrap(Session.class);
        SQLQuery query = session.createSQLQuery(sql);
        if (entities != null)
        {
            for (Object obj : entities)
            {
                Object[] objArr = (Object[]) obj;
                query.addEntity((String) objArr[0], (Class<?>) objArr[1]);
            }
        }

        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        if (page != -1 && pagesize != -1)
        {
            query.setFirstResult((page - 1) * pagesize);
            query.setMaxResults(pagesize);
        }
        return query.list();
    }


    public void executeHql(final String sql, final Object[] params)
    {
        Session session = entityManager.unwrap(Session.class);
        // session.connection().setAutoCommit(false);
        Query query = session.createQuery(sql);
        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        query.executeUpdate();
        // session.flush();
        // session.connection().commit();
    }


    public void executeSql(final String sql, final Object[] params)
    {
        Session session = entityManager.unwrap(Session.class);
        // session.connection().setAutoCommit(false);
        Query query = session.createSQLQuery(sql);
        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        query.executeUpdate();
        // session.flush();
        // session.connection().commit();
    }

    public void updateObject(Object obj)
    {
        entityManager.unwrap(Session.class).update(obj);
    }

    public int getObjectsCount(String sql, Object[] params)
    {
        if (!sql.startsWith("select"))
            sql = "select count(*)" + sql;
        List<?> alist = this.getAllObjects(sql, params);
        if (alist.size() > 0)
        {

            if (alist.get(0) != null)
                return ((Long) alist.get(0)).intValue();
        }

        return 0;
    }

    public int getObjectsCountBySql(final String sql, final Object[] params)
    {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(sql);
        if (params != null)
            for (int i = 0; i < params.length; i++)
                query.setParameter(i, params[i]);
        List aList = query.list();
        if (aList.size() > 0)
        {
            try
            {
                return ((java.math.BigInteger) aList.get(0)).intValue();
            }
            catch (Exception ex)
            {
                return ((Integer) aList.get(0));
            }
        }
        return 0;
    }


    public String organizeSql(String sql)
    {
        String rs = sql;
        if (sql.endsWith("and "))
        {
            rs = sql.substring(0, sql.lastIndexOf("and "));
        }

        if (sql.endsWith("where "))
        {
            rs = sql.substring(0, sql.lastIndexOf("where "));
        }

        return rs;
    }


    /**
     * delete record
     *
     * @param entity_name 实体类名称
     * @param id_column   id 列名
     * @param id          id值
     * @return result code
     */
    public int deleteById(String entity_name, String id_column, Object id)
    {
        if (entity_name == null || "".equals(entity_name)
                || id_column == null || "".equals(id_column)
                || id == null)
            return 1;

        this.executeHql("delete from " + entity_name + " where " + id_column + "=?", new Object[]{id});
        return 0;
    }

    /**
     * delete objects by ids
     *
     * @param entity_name 实体类名称
     * @param id_column   id 列名
     * @param ids         id值
     * @return result code
     */
    public int deleteByIds(String entity_name, String id_column, String ids)
    {
        if (entity_name == null || "".equals(entity_name)
                || id_column == null || "".equals(id_column)
                || ids == null || "".equals(ids))
            return 1;

        this.executeHql("delete from " + entity_name + " where " + id_column + " in (" + StrUtil.removeLawlessStr(ids) + ")", null);
        return 0;
    }

    /**
     * update object order, 移动排序
     *
     * @param entity_name  实体类名称
     * @param id_column    id 列名
     * @param order_column 排序字段列名
     * @param id           id值
     * @param direct       : asc, desc 向上调或者向下调
     */
    public void updateObjectOrder(String entity_name, String id_column, String order_column, Object id, String direct)
    {
        if (entity_name == null || "".equals(entity_name)
                || id_column == null || "".equals(id_column)
                || order_column == null || "".equals(order_column)
                || id == null
                || direct == null || "".equals(direct))
            return;

        // query the order index of current
        List alist = this.getObjectsByPage("select " + order_column + " from " + entity_name + " where " + id_column + "=?", new Object[]{id}, 1, 1);
        if (alist.size() > 0)
        {
            Long idx = (Long) alist.get(0);
            // query the silbling row id and order index
            List alist1 = null;
            if ("asc".equals(direct)) // up
                alist1 = this.getObjectsByPage("select " + id_column + ", " + order_column + " from " + entity_name + " where " + order_column + ">? order by " + order_column + " asc", new Object[]{idx}, 1, 1);
            else // down
                alist1 = this.getObjectsByPage("select " + id_column + ", " + order_column + " from " + entity_name + " where " + order_column + "<? order by " + order_column + " desc", new Object[]{idx}, 1, 1);
            if (alist1 != null && alist1.size() > 0)
            {
                Long idx1 = (Long) ((Object[]) alist1.get(0))[1];
                Long id1 = (Long) ((Object[]) alist1.get(0))[0];
                // exchange the order index of the two rows
                this.executeHql("update " + entity_name + " set " + order_column + "=? where " + id_column + "=?", new Object[]{idx1, id});
                this.executeHql("update " + entity_name + " set " + order_column + "=? where " + id_column + "=?", new Object[]{idx, id1});
            }
        }
    }


}
