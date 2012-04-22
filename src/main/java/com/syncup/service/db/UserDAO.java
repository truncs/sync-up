package com.syncup.service.db;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/21/12
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
import com.syncup.service.core.User;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.sqlobject.stringtemplate.ExternalizedSqlViaStringTemplate3;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

@ExternalizedSqlViaStringTemplate3
@RegisterMapperFactory(BeanMapperFactory.class)
public interface UserDAO {

    @SqlUpdate
    void createUserTable();

    @SqlQuery
    User findById(@Bind("id") Long id);

    @SqlQuery
    User findByLoginId(@Bind("loginId") String id);

    @SqlUpdate
    @GetGeneratedKeys
    long create(@BindBean User user);

    @SqlQuery
    ImmutableList<User> findAll();

}
