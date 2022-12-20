interface ${javaClassDefinition.PO.className}Vo {

<#list tableInfo.columnLists as colm>
 <#if colm??>
  /**
   * ${colm.columnComment}
   */
  ${colm.javaColumnName}?: ${colm.javaDataType} | null;
 </#if>

</#list>
}

export { ${javaClassDefinition.PO.className}Vo }