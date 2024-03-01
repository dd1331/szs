package com.jobis.jobis.szs;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class MemoryRegistrationValidator implements RegistrationValidator {
    private static final Map<String, String> allowedUsers = new HashMap<>();


    static {
        allowedUsers.put("동탁", "921108-1582816");
        allowedUsers.put("관우", "681108-1582816");
        allowedUsers.put("손권", "890601-2455116");
        allowedUsers.put("유비", "790411-1656116");
        allowedUsers.put("조조", "810326-2715702");
    }

    public boolean isAllowed(String name, String id) {
        String registeredId = allowedUsers.get(name);

        return registeredId != null && registeredId.equals(id);
    }
}