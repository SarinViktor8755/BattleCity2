package main.java.com;

import com.badlogic.gdx.math.MathUtils;

public class Service {
    public static boolean invertBoolean(boolean v){
        if(v) v = false; else v = true;
        System.out.println(v +" inbvert");
        return v;
    }

    public static boolean invertBooleanRandom(boolean v, float chance){
        if(v&& MathUtils.randomBoolean(chance)) v = false; else v =true;
        return v;
    }
}
