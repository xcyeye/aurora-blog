    <!-- ${methodDescription.INSERT.comment} -->
	<insert id="${methodDescription.INSERT.methodName}" parameterType="${mybatisXmlDefinition.parameterType}" useGeneratedKeys="true" keyProperty="uid">
        INSERT INTO ${tableInfo.tableName} (
            <trim suffixOverrides=",">
                <#list mybatisXmlDefinition.columns as colm>
                    <#if colm??>
                <if test="${colm.javaColumnName} != null">
                    ${colm.columnName},
                </if>
                    </#if>
                </#list>
            </trim>
        ) VALUES (
            <trim suffixOverrides=",">
            <#list mybatisXmlDefinition.columns as colm>
                <#if colm??>
                <if test="${colm.javaColumnName} != null">
                 ${'#'}{${colm.javaColumnName}},
                </if>
                </#if>
            </#list>
            </trim>
        )
	</insert>
