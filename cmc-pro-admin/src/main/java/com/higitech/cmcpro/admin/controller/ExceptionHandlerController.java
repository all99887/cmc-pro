package com.higitech.cmcpro.admin.controller;

import com.higitech.cmcpro.admin.model.CmcModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CmcModel handleException(Exception e){
        CmcModel cmcModel = new CmcModel();
        log.error("后台错误", e);
        cmcModel.addError("common.error.common");
        return cmcModel;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CmcModel handleException(MethodArgumentNotValidException  e){
        CmcModel cmcModel = new CmcModel();
        log.error("数据格式错误", e);
        cmcModel.addError("common.error.dataFormat");
        return cmcModel;
    }

}
