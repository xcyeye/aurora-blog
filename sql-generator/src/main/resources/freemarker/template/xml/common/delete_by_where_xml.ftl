    <!-- ${methodDescription.DELETE_BY_WHERE.comment} -->
	<delete id="${methodDescription.DELETE_BY_WHERE.methodName}" parameterType="${mybatisXmlDefinition.parameterType}">
        DELETE FROM au_${tableInfo.tableName}
        <include refid="Where_Condition" />
	</delete>
