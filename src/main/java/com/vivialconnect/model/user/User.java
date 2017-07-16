package com.vivialconnect.model.user;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.vivialconnect.model.ResourceCount;
import com.vivialconnect.model.VivialConnectResource;
import com.vivialconnect.model.error.NoContentException;
import com.vivialconnect.model.error.VivialConnectException;
import com.vivialconnect.model.format.JsonBodyBuilder;

@JsonRootName("user")
public class User extends VivialConnectResource{

    private static final long serialVersionUID = 6871296061820754520L;

    /** Unique identifier of the user object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the user in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of user in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the account or subaccount associated with the user
     */
    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty
    private boolean active;

    @JsonProperty
    private boolean verified;

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty
    private String timezone;

    @JsonProperty
    private String username;

    /** User's first name */
    @JsonProperty("first_name")
    private String firstName;

    /** User's last name */
    @JsonProperty("last_name")
    private String lastName;

    /** User's email address */
    @JsonProperty
    private String email;

    @JsonProperty
    private List<Role> roles;

    static {
        classesWithoutRootValue.add(UserCollection.class);
    }

    public static User getUserById(int userId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(User.class, String.valueOf(userId)), null, null, User.class);
    }


    public static List<User> getUsers() throws VivialConnectException{
        return getUsers(null);
    }


    public static List<User> getUsers(Map<String, String> queryParams) throws VivialConnectException{
        return request(RequestMethod.GET, classURL(User.class), null, queryParams, UserCollection.class).getUsers();
    }


    public static int count() throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(User.class, "count"), null, null, ResourceCount.class).getCount();
    }


    public boolean delete() throws VivialConnectException{
        try{
            request(RequestMethod.DELETE, classURLWithSuffix(User.class, String.valueOf(getId())), null, null, String.class);
        }catch (NoContentException nce){
            return true;
        }

        return false;
    }


    public boolean changePassword(String oldPassword, String newPassword) throws VivialConnectException{
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(User.class)
                                                 .addParamPair("_password", oldPassword)
                                                 .addParamPair("password", newPassword);

        String result = request(RequestMethod.PUT, classURLWithSuffix(User.class, String.format("%d/profile/password", getId())),
                                                                                            builder.build(), null, String.class);

        return "{}".equals(result);
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public Date getDateModified(){
        return dateModified;
    }

    public void setDateModified(Date dateModified){
        this.dateModified = dateModified;
    }

    public int getAccountId(){
        return accountId;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isVerified(){
        return verified;
    }

    public void setVerified(boolean verified){
        this.verified = verified;
    }

    public String getApiKey(){
        return apiKey;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getTimezone(){
        return timezone;
    }

    public void setTimezone(String timezone){
        this.timezone = timezone;
    }

    public List<Role> getRoles(){
        return roles;
    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }
}