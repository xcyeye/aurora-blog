	<!-- ${methodDescription.BATCH_INSERT.comment} -->
	<insert id="${methodDescription.BATCH_INSERT.methodName}" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
        INSERT All
        <foreach collection="list" item="item">
            INTO ${tableInfo.tableSchema}.${tableInfo.tableName} (
            <trim suffixOverrides=",">
		    <#list mybatisXmlDefinition.columns as colm>
			<#if colm??>
				<if test="null != item.${colm.javaColumnName}">${colm.columnName},</if>
            </#if>
            </#list>
            </trim>
            ) VALUES (
            <trim suffixOverrides=",">
			<#list mybatisXmlDefinition.columns as colm>
			<#if colm??>
				<if test="null != item.${colm.javaColumnName}">${'#'}{item.${colm.javaColumnName},jdbcType=${colm.jdbcType}},</if>
            </#if>
            </#list>
            </trim>
            )
        </foreach>
        SELECT 1 FROM DUAL
    </insert>
