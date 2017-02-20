package sample.study.andy.andydemos.function.javaApi;

/**
 * Created by Andy.chen on 2016/12/13.
 */

public class ChildClass extends Parent {
    @Override
    public void testA() {
        System.out.println("ChildClass ---testA");
        return;
    }

    @Override
    public void testB() {
        super.testB();
        System.out.println("ChildClass ---testB");
    }

    @Override
    public boolean testC() {
        super.testC();
        System.out.println("ChildClass ---testC");
        return true;
    }
}
