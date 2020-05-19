package com.zyw.online_exam.graduation_design;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 18:34
 */
@Getter
@Setter
@ToString
public class Dto {
    private  Object object;
    private  Integer state;
    private String message;
    private Integer total;

    public Dto(Object object, Integer state) {
        this.object = object;
        this.state = state;
        this.total=1;
    }
    public Dto( Integer state) {
        this.object = null;
        this.state = state;
        this.total=1;
    }
    public Dto(Object object, Integer state,String message) {
        this.object = object;
        this.state = state;
        this.message = message;
        this.total=1;
    }
    public Dto(Integer state,String message){
        this.state = state;
        this.message = message;
        this.total=1;
    }
    public Dto(Integer state,String message,Integer total){
        this.state = state;
        this.message = message;
        this.total=total;
    }
    public Dto(Integer state,String message,Integer total,Object o){
        this.state = state;
        this.message = message;
        this.total=total;
        this.object=o;
    }
    public static Dto getSuccess(Object o){
        return new Dto(o,1);
    }
    public static Dto getSuccess(){
        return new Dto(1);
    }

    public static Dto getFailed(){
        return new Dto(0);
    }

    public static Dto getFailed(Object oj){
        return new Dto(oj,0);
    }
    public static  Dto getSuccess(String message,Object o){
        return new Dto(o,1,message);
    }
    public static Dto getSuccess(String message,Object o,Integer total){
        return new Dto(1,message,total,o);
    }
    public static Dto getNoAuthority(String message){
        return new Dto(3,message);
    }

}
