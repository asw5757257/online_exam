package com.zyw.online_exam.graduation_design;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 18:34
 */
@Getter
@Setter
public class Dto {
    private  Object object;
    private  Integer state;

    public Dto(Object object, Integer state) {
        this.object = object;
        this.state = state;
    }
    public Dto( Integer state) {
        this.object = null;
        this.state = state;
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

}
