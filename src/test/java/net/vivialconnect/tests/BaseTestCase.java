package net.vivialconnect.tests;

import net.vivialconnect.tests.data.DataSource;
import net.vivialconnect.tests.data.DataSourceFactory;

public class BaseTestCase {

    private static final DataSource DATA_SOURCE = new DataSourceFactory().createDataSource();

    protected final DataSource getDataSource() {
        return DATA_SOURCE;
    }
}