package test.com.androidtest.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建日期：18/7/10 on 下午2:49.
 * 作者：liuxun
 * 描述：
 */

public class UserInter {
    public static final int childe = 0x1;
    public static final int man = 0x2;
    public static final int girl = 0x3;
    public static final int other = 0x4;

    private int userType;

    @IntDef({childe, man, girl})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UserInters {
    }

    @UserInters
    public int getUserType() {
        return userType;
    }

    public void setUserType(@UserInters int type) {
        userType = type;
    }

    public static class InnerClass{
        public InnerClass(){
            UserInter userInter = new UserInter();
            userInter.setUserType(childe);
        }
    }
}
