package com.syncup.service.db;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 5:54 AM
 * To change this template use File | Settings | File Templates.
 */
import com.syncup.service.core.Person;
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
public interface PeopleDAO {

    @SqlUpdate
    void createPeopleTable();

    @SqlQuery
    Person findById(@Bind("id") Long id);

    @SqlUpdate
    @GetGeneratedKeys
    long create(@BindBean Person person);

    @SqlQuery
    ImmutableList<Person> findAll();

    @SqlUpdate
    void dropPeopleTable();
}
