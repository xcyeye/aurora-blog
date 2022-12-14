    <!-- ${methodDescription.QUERY_ONE.comment}  -->
	<select id="${methodDescription.QUERY_ONE.methodName}" resultMap="${mybatisXmlDefinition.resultMap.id}" parameterType="${mybatisXmlDefinition.parameterType}">
         SELECT
         <include refid="Base_Column_List" />
  	     FROM au_${tableInfo.tableName}
         <include refid="Where_Condition" />
         LIMIT 1
    </select>
    