    <!-- ${methodDescription.BATCH_INSERT.comment} -->
	<insert id="${methodDescription.BATCH_INSERT.methodName}" parameterType="java.util.List">
        INSERT INTO au_${tableInfo.tableName} (
        	<include refid="Base_Column_List" />
        ) VALUES
        <foreach collection="list" item="item" index= "index" separator =",">
            (
            <trim suffixOverrides=",">
			<#list mybatisXmlDefinition.columns as colm>
			<#if colm??>
				${'#'}{item.${colm.javaColumnName}},
            </#if>
            </#list>
            </trim>
            )
        </foreach>
    </insert>
