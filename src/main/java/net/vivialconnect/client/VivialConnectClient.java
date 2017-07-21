package net.vivialconnect.client;

public final class VivialConnectClient {

    private static int accountId;

    private static String apiKey;
    private static String apiSecret;

    private static String apiBaseUrl = "https://api.vivialconnect.net/api/v1.0";

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

    public static String getApiBaseUrl() {
        return apiBaseUrl;
    }
    
    /**
    * (FOR TESTING ONLY) If you'd like your API requests to hit your own
    * (mocked) server, you can set this up here by overriding the base API URL.
    * 
     * @param apiBaseUrl
    */
    public static void overrideApiBaseUrl(String apiBaseUrl) {
        VivialConnectClient.apiBaseUrl = apiBaseUrl;
    }
    
    /**
     * Initializes the client by setting the accountId, the apiKey, and the apiSecret.
     * 
     * @param accountId the VivialConnect account ID
     * @param apiKey the VivialConnect API key
     * @param apiSecret the VivialConnect API secret
     * 
     * @throws IllegalArgumentException if any of the arguments are invalid (ie, null or empty)
     */
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
