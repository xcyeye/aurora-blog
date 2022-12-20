	/**
     * ${methodDescription.QUERY_BY_WHERE.comment} <br>
    <#list methodDescription.QUERY_BY_WHERE.params as param>
     * @param ${javaClassDefinition.POJO.className?uncap_first} ${param.comment}  <br>
    </#list>
     * @return 符合条件的数据集合
     */
    List<${javaClassDefinition.POJO.className}> ${methodDescription.QUERY_BY_WHERE.methodName}(${javaClassDefinition.POJO.className} ${javaClassDefinition.POJO.className?uncap_first});
