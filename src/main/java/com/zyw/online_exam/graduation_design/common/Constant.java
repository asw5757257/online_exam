package com.zyw.online_exam.graduation_design.common;


/**
 * @author chs
 * @name Constant
 * @description 常量类，存放一些常亮信息
 * @create 2017-10-16 9:27
 **/
public class Constant {
    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 学生
     */
    public static final String STUDENT = "student";

    /**
     * 教师
     */
    public static final String TEACHER = "teacher";

    public interface Role {
        /**
         * 管理员
         */
        String ROLE_ADMIN = "0";
        /**
         * 教师
         */
        String ROLE_TEACHER = "1";
        /**
         * 学生
         */
        String ROLE_STUDENT = "2";
    }

    public interface TestType {
        /**
         * 选择题
         */
        String TEST_CHOICE = "1";
        /**
         * 判断题
         */
        String TEST_JUDGE = "2";
        /**
         * 填空题
         */
        String TEST_FILL = "3";
    }

    /**
     * 有效
     */
    public static final String FLAG_Y = "Y";

    /**
     * 无效
     */
    public static final String FLAG_N = "N";


    public static final String TOKEN_PREFIX = "token_";

}
