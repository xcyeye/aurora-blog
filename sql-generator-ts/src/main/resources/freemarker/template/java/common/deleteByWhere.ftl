	/**
     * ${methodDescription.DELETE_BY_WHERE.comment} <br>
<#list methodDescription.DELETE_BY_WHERE.params as param>
     * @param ${javaClassDefinition.POJO.className?uncap_first} ${param.comment}  <br>
</#list>
     * @return 删除的数据条数
     */
    int ${methodDescription.DELETE_BY_WHERE.methodName}(${javaClassDefinition.POJO.className} ${javaClassDefinition.POJO.className?uncap_first});
