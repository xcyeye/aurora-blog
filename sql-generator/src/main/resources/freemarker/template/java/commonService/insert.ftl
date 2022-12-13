	/**
     * ${methodDescription.INSERT.comment} <br>
    <#list methodDescription.INSERT.params as param>
     * @param ${javaClassDefinition.POJO.className?uncap_first} ${param.comment}  <br>
    </#list>
     * @return 新增的数据条数
     */
	int ${methodDescription.INSERT.methodName}(${javaClassDefinition.POJO.className} ${javaClassDefinition.POJO.className?uncap_first});
