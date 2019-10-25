package com.example.invoker.base;

/**
 * @Author Liumq
 * @Date   2019/05/23
 */
public abstract class AbstractBuilder {

    public abstract Invoker build();

    public boolean isParamtersValid() {
        try {
            checkBeforeBuild();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public abstract void checkBeforeBuild() throws Exception;

}
