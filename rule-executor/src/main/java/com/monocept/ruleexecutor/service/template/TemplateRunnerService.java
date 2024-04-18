package com.monocept.ruleexecutor.service.template;

import com.monocept.ruleexecutor.model.NudgesEvent;
import com.monocept.ruleexecutor.model.Rules;

import java.util.stream.Stream;


public interface TemplateRunnerService {
    public Stream<NudgesEvent> prepareEvent(Rules rule);
    public void sendEvent(Stream<NudgesEvent> nudgesEvent);
}
