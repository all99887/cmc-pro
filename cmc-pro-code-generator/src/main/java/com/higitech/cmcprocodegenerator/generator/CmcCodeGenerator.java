package com.higitech.cmcprocodegenerator.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CmcCodeGenerator {

    private AutoGenerator autoGenerator;

    private String basePackage;

    public CmcCodeGenerator(AutoGenerator autoGenerator, String basePackage){
        this.autoGenerator = autoGenerator;
        this.basePackage = basePackage;
    }

    public void execute() throws Exception {
        AbstractTemplateEngine templateEngine = autoGenerator.getTemplateEngine();
        List<TableInfo> tableInfoList = templateEngine.getConfigBuilder().getTableInfoList();

        for (TableInfo tableInfo : tableInfoList) {
            Map<String, Object> objectMap = templateEngine.getObjectMap(tableInfo);
            Map<String, String> pathInfo = templateEngine.getConfigBuilder().getPathInfo();
            TemplateConfig template = templateEngine.getConfigBuilder().getTemplate();

            String entityName = tableInfo.getEntityName();

            String formClassPackage = basePackage + ".model.form";
            String formClassName = Objects.toString(objectMap.get("entity")) + "Form";
            String baseClassPath = pathInfo.get(ConstVal.CONTROLLER_PATH);
            String formClassPath = StrUtil.subBefore(baseClassPath, File.separator, true) + File.separator + "model" + File.separator + "form" + File.separator + formClassName + ConstVal.JAVA_SUFFIX;
            String vuePath = StrUtil.subBefore(baseClassPath, File.separator, true) + File.separator + "vue" + File.separator + entityName + ".vue";
            String finalXmlPath = StrUtil.subBefore(baseClassPath, File.separator, true) + File.separator + "xml" + File.separator + entityName + ".xml";

            objectMap.put("formClassPackage", formClassPackage);
            objectMap.put("formClassName", formClassName);
//            objectMap.put("formClassPath", formClassPath);

            // CmcForm.java
            if (null != formClassName && null != formClassPath) {
                String formFile = String.format(formClassPath, entityName);
                if (isCreate(formFile)) {
                    templateEngine.writer(objectMap, templateEngine.templateFilePath("/templates/cmcForm.java"), formFile);
                }
            }
            // CmcController.java
            if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + ConstVal.JAVA_SUFFIX), entityName);
                if (isCreate(controllerFile)) {
                    templateEngine.writer(objectMap, templateEngine.templateFilePath("/templates/cmcController.java"), controllerFile);
                }
            }
            // 前台vue
            if (null != entityName && null != vuePath) {
//                String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + ConstVal.JAVA_SUFFIX), entityName);
                if (isCreate(vuePath)) {
                    templateEngine.writer(objectMap, templateEngine.templateFilePath("/templates/frontView"), vuePath);
                }
            }

            //把xml文件挪出来
//            // MpMapper.xml
//            if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
//                String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
//
//                //                if (this.isCreate(xmlFile)) {
////                    this.writer(objectMap, this.templateFilePath(template.getXml()), xmlFile);
////                }
//
//            }
        }
    }

    protected boolean isCreate(String filePath) {
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            mkDir(file.getParentFile());
        }
        return !exist || autoGenerator.getTemplateEngine().getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    protected void mkDir(File file) {
        if (null != file) {
            if (file.getParentFile().exists()) {
                file.mkdir();
            } else {
                mkDir(file.getParentFile());
                file.mkdir();
            }
        }
    }

}
