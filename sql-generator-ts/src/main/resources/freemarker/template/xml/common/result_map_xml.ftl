    <resultMap id="${mybatisXmlDefinition.resultMap.id}" type="${mybatisXmlDefinition.resultMap.type}">
<#list mybatisXmlDefinition.columns as colm>
    <#if colm??>
        <#if colm.primaryKey>
        <id column="${colm.columnName}" property="${colm.javaColumnName}"/>
        <#else>
        <result column="${colm.columnName}" property="${colm.javaColumnName}"/>
        </#if>
    </#if>
</#list>
    </resultMap>
