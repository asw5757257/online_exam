package com.zyw.online_exam.graduation_design.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 17:35
 */
@Getter
@Setter
public class MyException extends  Exception {

    String message;
    public MyException(String message){
        super(message);
    }
}
