package ${formClassPackage};

import ${package.Entity}.${entity};
import lombok.Data;

<#--import javax.validation.constraints.NotBlank;-->
<#--import javax.validation.constraints.Size;-->
<#--import java.io.Serializable;-->
<#list table.importPackages as pkg>
import ${pkg};
</#list>

@Data
public class ${formClassName} implements Serializable{

    private static final long serialVersionUID = 1L;

<#list table.fields as field>
    private ${field.propertyType} ${field.propertyName};
</#list>

    public ${entity} toDbEntity(){
        ${entity} entity = new ${entity}();
<#list table.fields as field>
        entity.set${field.capitalName}(${field.propertyName});
</#list>
        return entity;
    }
}
