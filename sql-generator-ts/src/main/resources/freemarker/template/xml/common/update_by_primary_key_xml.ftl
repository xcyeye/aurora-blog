    <!-- ${methodDescription.UPDATE_BY_PRIMARY_KEY.comment} -->
    <update id="${methodDescription.UPDATE_BY_PRIMARY_KEY.methodName}" parameterType="${mybatisXmlDefinition.parameterType}">
		UPDATE au_${tableInfo.tableName}
		<set>
    <#list mybatisXmlDefinition.columns as colm>
        <#if colm??>
            <#if colm_index != 0 && "${colm.javaColumnName}"!="createTime">
			<if test="${colm.testNotBlankExpression}">${colm.columnName} = ${'#{'}${colm.javaColumnName}},</if>
		    </#if>
		</#if>
	</#list>
		</set>
		WHERE
	<#list tableInfo.primaryKeyColumns as colm>
		    <#if colm_index != 0>AND </#if>${colm.columnName} = ${'#{'}${colm.javaColumnName}}
	</#list>
	</update>
