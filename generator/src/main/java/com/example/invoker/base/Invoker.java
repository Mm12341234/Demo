package com.example.invoker.base;

import java.util.zip.ZipOutputStream;

/**
 * @Author Liumq
 * @Date   2019/05/23
 */
public interface Invoker {

     void execute(ZipOutputStream zip);
}
