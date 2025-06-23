package co.com.mueblessas.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class StatsEntity {

    private String timestamp;
    private Integer totalCustomerContacts;
    private Integer complaintReason;
    private Integer warrantyReason;
    private Integer questionReason;
    private Integer purchaseReason;
    private Integer praiseReason;
    private Integer exchangeReason;
    private String hash;

    public StatsEntity(String timestamp, Integer totalCustomerContacts,
                       Integer complaintReason, Integer warrantyReason,
                       Integer questionReason, Integer purchaseReason, Integer praiseReason,
                       Integer exchangeReason, String hash) {
        this.timestamp = timestamp;
        this.totalCustomerContacts = totalCustomerContacts;
        this.complaintReason = complaintReason;
        this.warrantyReason = warrantyReason;
        this.questionReason = questionReason;
        this.purchaseReason = purchaseReason;
        this.praiseReason = praiseReason;
        this.exchangeReason = exchangeReason;
        this.hash = hash;
    }

    public StatsEntity() {
        this.timestamp = timestamp;
        this.totalCustomerContacts = totalCustomerContacts;
        this.complaintReason = complaintReason;
        this.warrantyReason = warrantyReason;
        this.questionReason = questionReason;
        this.purchaseReason = purchaseReason;
        this.praiseReason = praiseReason;
        this.exchangeReason = exchangeReason;
        this.hash = hash;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("totalCustomerContacts")
    public Integer getTotalCustomerContacts() {
        return totalCustomerContacts;
    }

    public void setTotalCustomerContacts(Integer totalCustomerContacts) {
        this.totalCustomerContacts = totalCustomerContacts;
    }

    @DynamoDbAttribute("complaintReason")
    public Integer getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(Integer complaintReason) {
        this.complaintReason = complaintReason;
    }

    @DynamoDbAttribute("warrantyReason")
    public Integer getWarrantyReason() {
        return warrantyReason;
    }

    public void setWarrantyReason(Integer warrantyReason) {
        this.warrantyReason = warrantyReason;
    }

    @DynamoDbAttribute("questionReason")
    public Integer getQuestionReason() {
        return questionReason;
    }

    public void setQuestionReason(Integer questionReason) {
        this.questionReason = questionReason;
    }

    @DynamoDbAttribute("purchaseReason")
    public Integer getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(Integer purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    @DynamoDbAttribute("praiseReason")
    public Integer getPraiseReason() {
        return praiseReason;
    }

    public void setPraiseReason(Integer praiseReason) {
        this.praiseReason = praiseReason;
    }

    @DynamoDbAttribute("exchangeReason")
    public Integer getExchangeReason() {
        return exchangeReason;
    }

    public void setExchangeReason(Integer exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    @DynamoDbAttribute("hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /*



    public ModelEntity() {
    }



    @DynamoDbPartitionKey
    @DynamoDbAttribute("name")
    public String getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("atr1")
    public String getAtr1() {
        return atr1;
    }

    public void setAtr1(String atr1) {
        this.atr1 = atr1;
    }*/
}
