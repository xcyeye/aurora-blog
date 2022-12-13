    <!-- ${methodDescription.DELETE_BY_WHERE.comment} -->
	<delete id="${methodDescription.DELETE_BY_WHERE.methodName}" parameterType="${mybatisXmlDefinition.parameterType}">
        DELETE FROM ${tableInfo.tableName}
        <include refid="Where_Clause" />
	</delete>
