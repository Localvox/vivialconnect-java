package net.vivialconnect.tests.data;

public class DataSourceFactory {

    public DataSource createDataSource() {
        boolean useMockData = Boolean.getBoolean("vivialconnect.test.mock");

        if (useMockData) {
            return new MockData();
        } else {
            return new VivialConnectServer();
        }
    }
}