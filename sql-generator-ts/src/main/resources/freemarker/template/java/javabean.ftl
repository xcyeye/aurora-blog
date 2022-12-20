interface ${javaClassDefinition.PO.className} {

<#list tableInfo.columnLists as colm>
	<#if colm??>
	/**
	 * ${colm.columnComment}
	 */
	${colm.javaColumnName}?: ${colm.javaDataType} | null;
	</#if>

</#list>
<#--<#list tableInfo.columnLists as colm>-->
<#--	<#if colm??>-->
<#--	public ${colm.javaDataType} get${colm.javaColumnName?cap_first}() {-->
<#--		return ${colm.javaColumnName};-->
<#--	}-->

<#--	public void set${colm.javaColumnName?cap_first}(${colm.javaDataType} ${colm.javaColumnName}) {-->
<#--		this.${colm.javaColumnName} = ${colm.javaColumnName};-->
<#--	}-->
<#--	-->
<#--	</#if>-->
<#--</#list>-->
}