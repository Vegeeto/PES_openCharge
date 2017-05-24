package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.User;

import java.util.Map;

/**
 * Created by DmnT on 18/05/2017.
 */

public interface UsersParser {
    public User parseFromMap(String key, Map<String, Object> map);
}
