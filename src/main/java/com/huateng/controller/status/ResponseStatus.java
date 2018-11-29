package com.huateng.controller.status;/**
 * @author qs
 * @create 2018-10-10 08:59
 **/

/**
 *
 * @author shuaion 2018/10/10
 *
 * 控制器返回前端类
 * 枚举+Object对象
 *
 **/
public class ResponseStatus {

    //使用枚举类型定义常量
    public enum FLAG{

        SUCCESS("成功"),FAIL("失败"),ERROR("系统异常");

        FLAG(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private FLAG flag;
    private Object object;

    public ResponseStatus(FLAG flag) {
        this.flag = flag;
    }

    public ResponseStatus(FLAG flag, Object object) {
        this.flag = flag;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static void main(String[] args) {

    }

    public FLAG getFlag() {
        return flag;
    }

    public void setFlag(FLAG flag) {
        this.flag = flag;
    }
}
