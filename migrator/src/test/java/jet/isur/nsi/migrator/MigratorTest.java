package jet.isur.nsi.migrator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import jet.isur.nsi.api.NsiConfigManager;
import jet.isur.nsi.api.data.NsiConfig;
import jet.isur.nsi.common.config.impl.NsiConfigManagerFactoryImpl;
import jet.isur.nsi.migrator.hibernate.RecActionsTargetImpl;
import jet.isur.nsi.testkit.test.BaseSqlTest;
import jet.isur.nsi.testkit.utils.DaoUtils;
import junit.framework.Assert;

import org.junit.Test;

public class MigratorTest extends BaseSqlTest{

    private NsiConfig config;
    private String metadataPath;
    private MigratorParams params;

    @Override
    public void setup() throws Exception {
        super.setup();

        getConfiguration();

        File configPath = new File(metadataPath);
        NsiConfigManager manager = new NsiConfigManagerFactoryImpl().create(configPath);
        config = manager.getConfig();
        params = new MigratorParams(properties);
    }


    private void getConfiguration() throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties");
        Properties props = new Properties();
        props.load(in);
        metadataPath = "src/test/resources/metadata";
    }


    @Test
    public void migratorTest() throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            DaoUtils.dropTable("table2", connection);
            DaoUtils.dropTable("table1", connection);
            DaoUtils.dropSeq("seq_table2", connection);
            DaoUtils.dropSeq("SEQ_TABLE1", connection);

            DaoUtils.dropSeq("SEQ_POSTPROC1", connection);

            DaoUtils.dropTable("TEST_NSI_PREPARE_LOG", connection);
            DaoUtils.dropTable("TEST_NSI_POSTPROC_LOG", connection);
        }

        {
            Migrator migrator = new Migrator(config, dataSource, params, "TEST_NSI_" );
            RecActionsTargetImpl rec = new RecActionsTargetImpl();
            migrator.addTarget( rec );
            migrator.update("v1");

            List<String> actions = rec.getActions();
            Assert.assertEquals(4, actions.size());
            Assert.assertEquals("create table table1 (id number(19,0) not null, f1 varchar2(100 char), "
                    + "is_deleted char(1 char), last_change date, last_user number(19,0), "
                    + "primary key (id))", actions.get(0));
            Assert.assertEquals("create table table2 (id number(19,0) not null, dict1_id number(19,0), "
                    + "name char(100 char), is_deleted char(1 char), last_change date, last_user number(19,0), "
                    + "primary key (id))", actions.get(1));
            Assert.assertEquals("alter table table2 add constraint fk_table2_28129655 foreign key (dict1_id) references table1", actions.get(2));
            Assert.assertEquals("create sequence seq_table2 start with 1 increment by 1", actions.get(3));
        }

        // check SEQ_POSTPROC1
        try(Connection connection = dataSource.getConnection()) {
            DaoUtils.executeSql("select SEQ_POSTPROC1.nextval from dual", connection);
        }

        try(Connection connection = dataSource.getConnection()) {
            DaoUtils.executeSql("ALTER TABLE TABLE1 DROP COLUMN F1", connection);
        }

        {
            Migrator migrator = new Migrator(config, dataSource, params, "TEST_NSI_" );
            RecActionsTargetImpl rec = new RecActionsTargetImpl();
            migrator.addTarget( rec );
            migrator.update("v2");

            List<String> actions = rec.getActions();
            Assert.assertEquals(1, actions.size());
            Assert.assertEquals("alter table table1 add f1 varchar2(100 char)",actions.get(0));
        }

        {
            Migrator migrator = new Migrator(config, dataSource, params, "TEST_NSI_" );
            RecActionsTargetImpl rec = new RecActionsTargetImpl();
            migrator.addTarget( rec );
            migrator.rollback("v2");

            List<String> actions = rec.getActions();
            Assert.assertEquals(0, actions.size());
        }

    }


}
