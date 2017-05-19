package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.Comment;

import java.util.Map;

/**
 * Created by Oriol on 4/5/2017.
 */

public interface CommentsParser {
    Comment parseFromMap(String key, Map<String, Object> map);
}
