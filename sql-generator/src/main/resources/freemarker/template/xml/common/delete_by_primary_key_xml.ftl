<#assign parameterType = "${tableInfo.singlePrimaryKey?then(mybatisXmlDefinition.columns[0].parameterType,mybatisXmlDefinition.parameterType)}">
    <!-- ${methodDescription.DELETE_BY_PRIMARY_KEY.comment} -->
	<delete id="${methodDescription.DELETE_BY_PRIMARY_KEY.methodName}" parameterType="${parameterType}">
        DELETE FROM ${tableInfo.tableName}
        WHERE <#list tableInfo.primaryKeyColumns as colm><#if colm_index != 0>AND </#if>${colm.columnName} = ${'#{'}${colm.javaColumnName}} </#list>
	</delete>
