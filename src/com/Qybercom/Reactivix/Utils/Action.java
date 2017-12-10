package com.Qybercom.Reactivix.Utils;

import java.util.function.Function;

/**
 * Interface Action
 *
 * https://habrahabr.ru/post/213805/
 * https://stackoverflow.com/questions/2015972/is-there-a-javadoc-tag-for-documenting-generic-type-parameters
 * https://www.tutorialspoint.com/java/java_documentation.htm
 * https://stackoverflow.com/questions/1184418/javas-equivalents-of-func-and-action
 * https://stackoverflow.com/questions/35934103/java-equivalent-for-system-action-in-net
 * https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
 * https://stackoverflow.com/questions/14319787/how-to-specify-function-types-for-void-not-void-methods-in-java8
 * https://javadevblog.com/java-callable-kratkoe-opisanie-i-primer-ispol-zovaniya.html
 * https://stackoverflow.com/questions/22795563/how-to-use-callable-with-void-return-type
 * https://stackoverflow.com/questions/15260596/generic-callback-in-java
 * https://stackoverflow.com/questions/11083868/is-there-an-interface-similar-to-callable-but-with-arguments
 * http://www.logicbig.com/tutorials/core-java-tutorial/java-8-enhancements/lambda-expression/
 * https://www.concretepage.com/java/jdk-8/java-8-runnable-and-callable-lambda-example-with-argument
 * https://stackoverflow.com/questions/9992992/is-there-a-way-to-take-an-argument-in-a-callable-method
 * https://stackoverflow.com/questions/36881826/what-is-use-of-functional-interface-in-java-8/36882003
 * https://stackoverflow.com/questions/44912/java-delegates
 * http://www.onjava.com/pub/a/onjava/2003/05/21/delegates.html
 * https://stackoverflow.com/questions/39346343/java-equivalent-of-c-sharp-delegates-queues-methods-of-various-classes-to-be-ex
 *
 * @param <TArg>
 */
public interface Action<TArg> extends Function<TArg, Void> { }