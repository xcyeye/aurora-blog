	<!-- ${methodDescription.COUNT_BY_WHERE.comment} -->
	<select id="${methodDescription.COUNT_BY_WHERE.methodName}" resultType="int" parameterType="${mybatisXmlDefinition.parameterType}">
		SELECT COUNT(*) FROM au_${tableInfo.tableName}
		<include refid="Where_Condition" />
	</select>
