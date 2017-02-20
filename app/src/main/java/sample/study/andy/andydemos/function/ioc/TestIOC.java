package sample.study.andy.andydemos.function.ioc;

import android.widget.TextView;

import java.lang.reflect.Field;

import sample.study.andy.andydemos.R;

/**
 * Created by Andy.chen on 2016/11/7.
 */

public class TestIOC {
    @AndyAnnotation(id = R.id.button,click = true)
    private TextView textview;


    public static void main(String [] strings) {
        TestIOC testIOC = new TestIOC();
        testIOC.findIOCValue();
    }

    private void findIOCValue() {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            AndyAnnotation andyAnnotation = field.getAnnotation(AndyAnnotation.class);
            int viewId = andyAnnotation.id();  //这是我们传的id
            boolean clickLis = andyAnnotation.click(); //这是我们传的click

            System.out.println("id = "+viewId + "clickLis = "+clickLis);
        }
    }
}
