<#if tableInfo.singlePrimaryKey>
	/**
     * ${methodDescription.QUERY_BY_PRIMARY_KEY.comment} <br>
     * @param ${tableInfo.primaryKeyColumns[0].javaColumnName?uncap_first} ${tableInfo.primaryKeyColumns[0].columnComment}  <br>
     * @return ${javaClassDefinition.POJO.className} 数据对象
     */
    ${javaClassDefinition.POJO.className} ${methodDescription.QUERY_BY_PRIMARY_KEY.methodName}(${tableInfo.primaryKeyColumns[0].javaDataType} ${tableInfo.primaryKeyColumns[0].javaColumnName?uncap_first});
<#else>
	/**
     * ${methodDescription.QUERY_BY_PRIMARY_KEY.comment} <br>
    <#list methodDescription.QUERY_BY_PRIMARY_KEY.params as param>
     * @param ${javaClassDefinition.POJO.className?uncap_first} ${param.comment}  <br>
    </#list>
     * @return ${javaClassDefinition.POJO.className} 数据对象
     */
    ${javaClassDefinition.POJO.className} ${methodDescription.QUERY_BY_PRIMARY_KEY.methodName}(${javaClassDefinition.POJO.className} ${javaClassDefinition.POJO.className?uncap_first});
</#if>
