package com.github.zhuyizhuo.generator.mybatis.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * class: MethodDescription <br>
 * description: 方法描述 <br>
 * time: 2019/5/21
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class MethodDescription {
    /** 是否生成方法 */
    private boolean enabled;
    /** 方法名 */
    private String methodName;
    /** 方法注释 */
    private String comment;
    /** 方法参数 */
    private List<ParamDescription> params = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ParamDescription> getParams() {
        return params;
    }

    public void addParams(ParamDescription param) {
        if (param == null){
            return;
        }
        this.params.add(param);
    }

    /**
     *  方法参数描述
     */
    public class ParamDescription {
        /** 注释 */
        private String comment;

        public ParamDescription(String comment) {
            this.comment = comment;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "ParamDescription{" +
                    "comment='" + comment + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MethodDescription{" +
                "enabled=" + enabled +
                ", methodName='" + methodName + '\'' +
                ", comment='" + comment + '\'' +
                ", params=" + params +
                '}';
    }
}
