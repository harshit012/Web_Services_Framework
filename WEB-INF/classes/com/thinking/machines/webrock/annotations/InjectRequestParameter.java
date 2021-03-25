package com.thinking.machines.webrock.annotations;
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})

public @interface InjectRequestParameter
{
String value() default "";
}
