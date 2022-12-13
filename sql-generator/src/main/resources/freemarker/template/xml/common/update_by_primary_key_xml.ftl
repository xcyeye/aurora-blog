    <!-- ${methodDescription.UPDATE_BY_PRIMARY_KEY.comment} -->
    <update id="${methodDescription.UPDATE_BY_PRIMARY_KEY.methodName}" parameterType="${mybatisXmlDefinition.parameterType}">
		UPDATE ${tableInfo.tableName}
		<set>
    <#list tableInfo.columnLists as colm>
        <#if colm??>
            <#if colm_index != 0>
			<if test="${colm.javaColumnName} != null">${colm.columnName} = ${'#{'}${colm.javaColumnName}},</if>
		    </#if>
		</#if>
	</#list>
		</set>
		WHERE
	<#list tableInfo.primaryKeyColumns as colm>
		    <#if colm_index != 0>AND </#if>${colm.columnName} = ${'#{'}${colm.javaColumnName}}
	</#list>
	</update>
