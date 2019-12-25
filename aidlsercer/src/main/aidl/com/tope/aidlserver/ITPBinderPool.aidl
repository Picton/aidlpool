// IBinderQuery.aidl
package com.tope.aidlserver;

import com.tope.aidlserver.IMdmQueryTokenAidlInterface;
import com.tope.aidlserver.IMdmUserAidlInterface;


interface ITPBinderPool {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    IBinder queryBinder(int binderCode);

}
