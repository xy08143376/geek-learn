package com.geek.gmq.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author itguoy
 * @date 2021-12-15 14:52
 */
@Data
@AllArgsConstructor
public class GmqMessage<T> implements Serializable {

    private HashMap<String, Object> headers;

    private T body;
}
