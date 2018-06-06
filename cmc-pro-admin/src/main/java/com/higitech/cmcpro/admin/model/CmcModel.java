package com.higitech.cmcpro.admin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通用返回数据
 * @author liuyanxiang
 */
public class CmcModel extends HashMap {

    /**
     * 返回分页数据时的key
     */
    public static final String PAGE = "page";

    /**
     * 返回列表数据时的key
     */
    public static final String LIST = "list";

    /**
     * 业务错误
     */
    public static final String ERROR = "error";

    /**
     * 处理成功
     */
    public static final String SUCCESS = "success";

    public CmcModel(){
        set(SUCCESS, true);
    }

    /**
     * 添加一个错误信息
     * @param message
     */
    public CmcModel addError(String message){
        if(containsKey(message)){
            List list = (List)get(ERROR);
            list.add(message);
        }else{
            List list = new ArrayList();
            list.add(message);
            set(ERROR, list);
        }
        set(SUCCESS, false);
        return this;
    }

    public CmcModel set(String attributeName, Object o){
        super.put(attributeName, o);
        return this;
    }

    public CmcModel setPage(Object o){
        return set(PAGE, o);
    }

    public CmcModel setList(Object o){
        return set(LIST, o);
    }

    public boolean isSuccess(){
        List list = (List)get(ERROR);
        if(list == null) {
            return true;
        }
        return list.isEmpty();
    }

}
