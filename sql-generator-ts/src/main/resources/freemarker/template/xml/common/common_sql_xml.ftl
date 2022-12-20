  	<sql id="Where_Condition">
        <where>
<#list mybatisXmlDefinition.columns as colm>
    <#if colm??>
            <if test="${colm.testNotBlankExpression}">
                AND ${colm.columnName} = ${'#'}{${colm.javaColumnName}}
            </if>
    </#if>
</#list>
        </where>
  	</sql>

    <sql id="Base_Column_List">
<#list mybatisXmlDefinition.columns as colm>
    <#if colm??>
        <#if colm_has_next>
        ${colm.columnName},
        <#else>
        ${colm.columnName}
        </#if>
    </#if>
</#list>
    </sql>
