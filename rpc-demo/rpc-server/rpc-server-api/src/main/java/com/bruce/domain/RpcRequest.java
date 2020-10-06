package com.bruce.domain;

import lombok.Data;

import java.io.Serializable;

/**
 *@description RPC传输数据
 *@data 2020/10/6 19:21
 *@author Bruce.Ren
 *@version 1.0
**/
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 5824758606899432981L;

    private String className;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
}
