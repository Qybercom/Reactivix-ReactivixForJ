package com.Qybercom.Reactivix.Utils;

import java.util.function.Function;

/**
 * @param <TArg>
 */
public interface Action<TArg> extends Function<TArg, Void> { }