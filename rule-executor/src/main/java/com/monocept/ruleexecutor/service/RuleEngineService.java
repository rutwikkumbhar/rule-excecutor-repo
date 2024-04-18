package com.monocept.ruleexecutor.service;

import com.monocept.ruleexecutor.error.FetchApiException;
import com.monocept.ruleexecutor.model.Rules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class RuleEngineService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${rules-engine.url}")
    private String ruleEngineUrl;

    public Optional<Rules> getRulesById(String nudgeId) {
        String url = ruleEngineUrl + "/api/rule/engine/v1/rule/fetchRuleByType/" + nudgeId;
        try {
            ResponseEntity<Rules> response = restTemplate.getForEntity(url, Rules.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Rules responseBody = response.getBody();
                log.debug("Response from external API: {}", responseBody);
                return Optional.ofNullable(responseBody);
            } else {
                log.error("No rule found with id {}", nudgeId);
                throw new FetchApiException("No rule found and got error response from rule engine");
            }
        } catch (HttpClientErrorException ex) {
            log.error("Client error: {} - {}", ex.getStatusCode(), ex.getStatusText());
            throw ex;
        } catch (RestClientException ex) {
            log.error("Error occurred: {}", ex.getMessage());
            throw ex;
        }
    }

}
