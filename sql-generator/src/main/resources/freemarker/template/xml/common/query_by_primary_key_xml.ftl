	<!-- ${methodDescription.QUERY_BY_PRIMARY_KEY.comment}  -->
<#if tableInfo.singlePrimaryKey>
	<select id="${methodDescription.QUERY_BY_PRIMARY_KEY.methodName}" resultMap="${mybatisXmlDefinition.resultMap.id}" parameterType="${mybatisXmlDefinition.columns[0].parameterType}">
<#else>
    <select id="${methodDescription.QUERY_BY_PRIMARY_KEY.methodName}" resultMap="${mybatisXmlDefinition.resultMap.id}" parameterType="${mybatisXmlDefinition.parameterType}">
</#if>
        SELECT
        <include refid="Base_Column_List" />
  	    FROM au_${tableInfo.tableName}
        WHERE
        <#list tableInfo.primaryKeyColumns as colm>
            <#if colm_index != 0>AND </#if>${colm.columnName} = ${'#{'}${colm.javaColumnName}}
       </#list>
    </select>
