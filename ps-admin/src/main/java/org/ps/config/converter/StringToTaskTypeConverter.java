package org.ps.config.converter;

import org.ps.enums.TaskType;
import org.springframework.core.convert.converter.Converter;

/**
 * String->TaskType转换器
 */
public class StringToTaskTypeConverter implements Converter<String, TaskType> {

    @Override
    public TaskType convert(String s) {
        return TaskType.getByValue(s);
    }
}
