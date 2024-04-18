package com.monocept.ruleexecutor.util;

import com.monocept.ruleexecutor.model.enums.DDupeCheckTypes;

import java.util.*;

import static com.monocept.ruleexecutor.model.enums.DDupeCheckTypes.*;

public class Utils {

    public static boolean compareList(List<String> ls1, List<String> ls2) {
        return new HashSet<>(ls1).containsAll(ls2) && ls1.size() == ls2.size();
    }

    public static Optional<DDupeCheckTypes> getDDupeCheck(List<String> ls) {
        List<String> list = ls.stream().map(String::toUpperCase).toList();
        if (compareList(Arrays.asList("NUDGE_ID", "SENDER"), list)) {
            return Optional.of(NUDGE_ID_SENDER);
        } else if (list.size() == 1 && compareList(List.of("SENDER"), list)) {
            return Optional.of(SENDER);
        } else if (list.size() == 1 && compareList(List.of("NUDGE_ID"), list)) {
            return Optional.of(NUDGE_ID);
        } else return Optional.empty();
    }
}
