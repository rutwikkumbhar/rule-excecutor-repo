package com.monocept.ruleexecutor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "leads")
public class Leads {
    public String agentId;
    public String customerName;
    public String mobileNumber;
    public String gender;
    public String catSource;
    public Date dob;
    public String alternateMobileNo;
    public String email;
    public String nationality;
    public String occupation;
    public String education;
    public String houseNo;
    public String pinCode;
    public String city;
    public String commHouseNo;
    public String commPinCode;
    public String commCity;
    public String campaign;
    public String investment;
    public String income;
    public String leadStatus;
    @Id
    public String leadId;
    public String vymoId;
    public String probabilityClosure;
    public String mainCampaign;
    public String statusCodeId;
    public String productId;
    public String ageRange;
    public String leadOwnerId;
    public String assignToCode;
    public String layoutId;
    public String ratingId;
    public String vymoRatingId;
    public String completeAddress;
    public Timestamp fmdDate;
    public Timestamp lastActivityTime;
    public String customerId;
    public String selectStatus;
    public String callConnected;
    public String callNotConnected;
    public String leadPropensity;
    public String productPitched;
    public String depedendantProductPitched;
    public String remarks;
    public String leadPriority;
    public String crmRatingid;
    public Timestamp createTime;
    public Timestamp updateTime;
}
