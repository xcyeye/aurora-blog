<#if tableInfo.singlePrimaryKey>
	/**
     * ${methodDescription.DELETE_BY_PRIMARY_KEY.comment} <br>
     * @param ${tableInfo.primaryKeyColumns[0].javaColumnName?uncap_first} ${tableInfo.primaryKeyColumns[0].columnComment}  <br>
     * @return 删除的数据条数
     */
    int ${methodDescription.DELETE_BY_PRIMARY_KEY.methodName}(${tableInfo.primaryKeyColumns[0].javaDataType} ${tableInfo.primaryKeyColumns[0].javaColumnName?uncap_first});
<#else>
	/**
     * ${methodDescription.DELETE_BY_PRIMARY_KEY.comment} <br>
<#list methodDescription.DELETE_BY_PRIMARY_KEY.params as param>
     * @param ${javaClassDefinition.POJO.className?uncap_first} ${param.comment}  <br>
</#list>
     * @return 删除的数据条数
     */
    int ${methodDescription.DELETE_BY_PRIMARY_KEY.methodName}(${javaClassDefinition.POJO.className} ${javaClassDefinition.POJO.className?uncap_first});
</#if>
