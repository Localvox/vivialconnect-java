package com.vivialconnect.model.account;

import java.lang.reflect.Field;
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

@JsonRootName("contact")
public class Contact extends VivialConnectResource{

    private static final long serialVersionUID = 3140451099385557777L;

    private static String[] REQUIRED_FIELDS = { "firstName", "lastName", "email", "contactType" };

    /** Unique identifier of the user object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the contact in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of the contact in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the parent account
     */
    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty
    private boolean active;

    @JsonProperty
    private String address1;

    @JsonProperty
    private String address2;

    @JsonProperty
    private String address3;

    @JsonProperty
    private String city;

    /** Name of company */
    @JsonProperty("company_name")
    private String companyName;

    /** The type of contact. Can be one of: ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing.’ */
    @JsonProperty("contact_type")
    private String contactType;

    @JsonProperty
    private String country;

    /** Contacts's email address */
    @JsonProperty
    private String email;

    /** Contacts's fax number */
    @JsonProperty
    private String fax;

    /** Contacts's first name */
    @JsonProperty("first_name")
    private String firstName;

    /** Contacts's last name */
    @JsonProperty("last_name")
    private String lastName;

    /** Contacts's mobile phone number */
    @JsonProperty("mobile_phone")
    private String mobilePhone;

    /** Contacts's postal code */
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty
    private String state;

    /** Contacts's position or title */
    @JsonProperty
    private String title;

    /** Contacts's work phone number */
    @JsonProperty("work_phone")
    private String workPhone;

    private JsonBodyBuilder jsonBodyBuilder;

    static {
        classesWithoutRootValue.add(ContactCollection.class);
    }

    public Contact(){
        jsonBodyBuilder = JsonBodyBuilder.forClass(Contact.class);
    }


    public Contact(Contact contact){
        jsonBodyBuilder = JsonBodyBuilder.forClass(Contact.class).addParamPair("id", contact.getId());
        updateObjectState(contact);
    }


    public Contact create() throws VivialConnectException{
        verifyRequiredFields();
        Contact createdContact = request(RequestMethod.POST, classURL(Contact.class),
                                        jsonBodyForCreate(), null, Contact.class);

        updateObjectState(createdContact);
        
        return this;
    }


    private void verifyRequiredFields(){
        Class<?> c = getClass();
        for (int i = 0; i < REQUIRED_FIELDS.length; i++){
            String requiredFieldName = REQUIRED_FIELDS[i];
            Field requiredField = getDeclaredField(c, requiredFieldName);

            if (requiredField == null){
                continue;
            }

            if (requiredField.getType().equals(String.class)){
                String fieldValue = (String) getValueFromField(requiredField);
                validateStringValue(requiredField.getName(), fieldValue);
            }
        }
    }


    private Field getDeclaredField(Class<?> clazz, String fieldName){
        try{
            return clazz.getDeclaredField(fieldName);
        }
        catch (SecurityException e) {}
        catch (NoSuchFieldException e) {}

        return null;
    }


    private Object getValueFromField(Field field){
        try{
            return field.get(this);
        }
        catch (IllegalArgumentException e) {}
        catch (IllegalAccessException e) {}

        return null;
    }


    private void validateStringValue(String fieldName, String fieldValue){
        if (fieldValue == null || fieldValue.isEmpty()){
            throw new IllegalStateException(String.format("Parameter '%s' is null or empty", fieldName));
        }
    }


    private String jsonBodyForCreate(){
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Contact.class);
        addRequiredFields(builder);
        addOptionalFields(builder);

        return builder.build();
    }


    private void addRequiredFields(JsonBodyBuilder builder){
        /* TODO: Move all these to class-level constants */
        ifParamValidAddToBuilder(builder, "first_name", getFirstName());
        ifParamValidAddToBuilder(builder, "last_name", getLastName());
        ifParamValidAddToBuilder(builder, "email", getEmail());
        ifParamValidAddToBuilder(builder, "contact_type", getContactType());
    }


    private void addOptionalFields(JsonBodyBuilder builder){
        ifParamValidAddToBuilder(builder, "company_name", getCompanyName());
        ifParamValidAddToBuilder(builder, "title", getTitle());
        ifParamValidAddToBuilder(builder, "address1", getAddress1());
        ifParamValidAddToBuilder(builder, "address2", getAddress2());
        ifParamValidAddToBuilder(builder, "address3", getAddress3());
        ifParamValidAddToBuilder(builder, "city", getCity());
        ifParamValidAddToBuilder(builder, "state", getState());
        ifParamValidAddToBuilder(builder, "postal_code", getPostalCode());
        ifParamValidAddToBuilder(builder, "country", getState());
        ifParamValidAddToBuilder(builder, "mobile_phone", getMobilePhone());
        ifParamValidAddToBuilder(builder, "work_phone", getWorkPhone());
    }


    private void updateObjectState(Contact contact){
        this.id = contact.getId();
        this.accountId = contact.getAccountId();
        this.active = contact.isActive();
        this.address1 = contact.getAddress1();
        this.address2 = contact.getAddress2();
        this.address3 = contact.getAddress3();
        this.city = contact.getCity();
        this.companyName = contact.getCompanyName();
        this.contactType = contact.getContactType();
        this.country = contact.getCountry();
        this.dateCreated = contact.getDateCreated();
        this.dateModified = contact.getDateModified();
        this.email = contact.getEmail();
        this.fax = contact.getFax();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.mobilePhone = contact.getMobilePhone();
        this.postalCode = contact.getPostalCode();
        this.state = contact.getState();
        this.title = contact.getTitle();
        this.workPhone = contact.getWorkPhone();
    }


    public Contact update() throws VivialConnectException{
        verifyRequiredFields();
        Contact updatedContact = request(RequestMethod.PUT, classURLWithSuffix(Contact.class, String.valueOf(getId())),
                                        jsonBodyForUpdate(), null, Contact.class);
        updateObjectState(updatedContact);
        return this;
    }


    public String jsonBodyForUpdate(){
        return jsonBodyBuilder.build();
    }


    public boolean delete() throws VivialConnectException{
        try{
            request(RequestMethod.DELETE, classURLWithSuffix(Contact.class, String.valueOf(getId())), null, null, String.class);
        }catch(NoContentException nce){
            return true;
        }

        return false;
    }


    public static List<Contact> getContacts() throws VivialConnectException{
        return getContacts(null);
    }


    public static List<Contact> getContacts(Map<String, String> queryParams) throws VivialConnectException{
        return request(RequestMethod.GET, classURL(Contact.class), null, queryParams, ContactCollection.class).getContacts();
    }


    public static Contact getContactById(int contactId) throws VivialConnectException{
        return new Contact(request(RequestMethod.GET, classURLWithSuffix(Contact.class, String.valueOf(contactId)), null, null, Contact.class));
    }


    public static int count() throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Contact.class, "count"), null, null, ResourceCount.class).getCount();
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
        ifParamValidAddToBuilder(jsonBodyBuilder, "id", getId());
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
        ifParamValidAddToBuilder(jsonBodyBuilder, "account_id", getAccountId());
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
        jsonBodyBuilder.addParamPair("active", isActive());
    }

    public String getAddress1(){
        return address1;
    }

    public void setAddress1(String address1){
        this.address1 = address1;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address1", getAddress1());
    }

    public String getAddress2(){
        return address2;
    }

    public void setAddress2(String address2){
        this.address2 = address2;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address2", getAddress2());
    }

    public String getAddress3(){
        return address3;
    }

    public void setAddress3(String address3){
        this.address3 = address3;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address3", getAddress3());
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
        ifParamValidAddToBuilder(jsonBodyBuilder, "city", getCity());
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "company_name", getCompanyName());
    }

    public String getContactType(){
        return contactType;
    }

    public void setContactType(String contactType){
        this.contactType = contactType;
        ifParamValidAddToBuilder(jsonBodyBuilder, "contact_type", getContactType());
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
        ifParamValidAddToBuilder(jsonBodyBuilder, "country", getCountry());
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
        ifParamValidAddToBuilder(jsonBodyBuilder, "email", getEmail());
    }

    public String getFax(){
        return fax;
    }

    public void setFax(String fax){
        this.fax = fax;
        ifParamValidAddToBuilder(jsonBodyBuilder, "fax", getFax());
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "first_name", getFirstName());
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "last_name", getLastName());
    }

    public String getMobilePhone(){
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone){
        this.mobilePhone = mobilePhone;
        ifParamValidAddToBuilder(jsonBodyBuilder, "mobile_phone", getMobilePhone());
    }

    public String getPostalCode(){
        return postalCode;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
        ifParamValidAddToBuilder(jsonBodyBuilder, "postal_code", getPostalCode());
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
        ifParamValidAddToBuilder(jsonBodyBuilder, "state", getState());
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
        ifParamValidAddToBuilder(jsonBodyBuilder, "title", getTitle());
    }

    public String getWorkPhone(){
        return workPhone;
    }

    public void setWorkPhone(String workPhone){
        this.workPhone = workPhone;
        ifParamValidAddToBuilder(jsonBodyBuilder, "work_phone", getWorkPhone());
    }
}