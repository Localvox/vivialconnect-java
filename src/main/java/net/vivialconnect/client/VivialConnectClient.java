package net.vivialconnect.client;

public final class VivialConnectClient {

    private static int accountId;

    private static String apiKey;
    private static String apiSecret;

    public static String apiBaseUrl = "https://api.vivialconnect.net/api/v1.0";

    private VivialConnectClient() {

    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getApiSecret() {
        return apiSecret;
    }

    public static int getAccountId() {
        return accountId;
    }

    public static void init(int accountId, String apiKey, String apiSecret) {
        validateInitialArguments(accountId, apiKey, apiSecret);

        VivialConnectClient.accountId = accountId;
        VivialConnectClient.apiKey = apiKey;
        VivialConnectClient.apiSecret = apiSecret;
    }

    private static void validateInitialArguments(int accountId, String apiKey, String apiSecret) {
        validateAccountId(accountId);
        validateAPIKey(apiKey);
        validateAPISecret(apiSecret);
    }

    private static void validateAccountId(int accountId) {
        if (accountId < 1) {
            throw createIllegalArgumentException("accountId");
        }
    }

    private static void validateAPIKey(String apiKey) {
        validateStringArg(apiKey, "apiKey");
    }

    private static void validateAPISecret(String apiSecret) {
        validateStringArg(apiSecret, "apiSecret");
    }

    private static void validateStringArg(String arg, String argName) {
        if (arg == null || arg.isEmpty()) {
            throw createIllegalArgumentException(argName);
        }
    }

    private static IllegalArgumentException createIllegalArgumentException(String argName) {
        return new IllegalArgumentException(String.format("'%s' param is not valid", argName));
    }
}
