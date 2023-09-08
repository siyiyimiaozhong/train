package com.siyi.train.${module}.dto;

<#list typeSet as type>
    <#if type=='Date'>
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
    </#if>
    <#if type=='BigDecimal'>
import java.math.BigDecimal;
    </#if>
</#list>
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${Domain}SaveDto implements Serializable {

<#list fieldList as field>
    /**
    * ${field.comment}
    */
    <#if field.javaType=='Date'>
        <#if field.type=='time'>
            @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
        <#elseif field.type=='date'>
            @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
        <#else>
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        </#if>
    </#if>
    <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
        <#if !field.nullAble>
            <#if field.javaType=='String'>
                @NotBlank(message = "【${field.nameCn}】不能为空")
            <#else>
                @NotNull(message = "【${field.nameCn}】不能为空")
            </#if>
        </#if>
    </#if>
    private ${field.javaType} ${field.nameHump};
</#list>
}
