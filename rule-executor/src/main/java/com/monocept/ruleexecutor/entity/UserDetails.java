package com.monocept.ruleexecutor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    public String ssoId;
    public String agentCode;
    public String name;
    public String username;
    public String deviceId;
    public String role;
    public String operatingSystem;
    public String status;
    public String email;
    public String phoneNumber;
    public String createdDate;
    public String updatedDate;
    public String createdBy;
    public String updatedBy;
    public String supervisorId;
    public String internalPersonCode;
    public String designation;
    public String designationName;
    public String channelCode;
    public String superAppRole;
    public String jwtToken;
    public String goCode;
    public String encPassword;
}


