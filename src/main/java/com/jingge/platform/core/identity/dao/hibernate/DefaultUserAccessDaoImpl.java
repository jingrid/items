/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.identity.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.jingge.platform.core.identity.dao.DefaultUserAccessDao;
import com.jingge.platform.core.identity.dataobject.DefaultUserAccess;

/**
 * <p>
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 * 
 * </pre>
 * 
 * <p><dt><b>Immutability:</b></dt> 
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 * 	
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	2012-7-29	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Repository( "defaultUserAccessDao" )
public class DefaultUserAccessDaoImpl extends AbstractUserAccessDaoImpl<DefaultUserAccess> implements DefaultUserAccessDao{

    @Override
    public DefaultUserAccess loadByUsername(final String username) {
        DefaultUserAccess userAccess = getHibernateTemplate().execute( new HibernateCallback<DefaultUserAccess>(){
            @Override
            public DefaultUserAccess doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria( dataObjectClass );
                criteria.add( Restrictions.eq( "username", username ) );
                return (DefaultUserAccess)criteria.uniqueResult();
            }
        } );
        return userAccess;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DefaultUserAccess> listByUserIds(List<String> userIds) {
        final String hql = "from " + DefaultUserAccess.class.getName() + " as entity where entity.userId in ";
        return (List<DefaultUserAccess>)getHibernateTemplate().find( hql + "(" + StringUtils.join( userIds, ',' ) + ") order by entity.username ASC" );
    }
    
}
