package com.dzz.support.constant;

/**
 * system constant
 *
 * @author Jeffrey
 * @since 2017/06/30 13:48
 */
public final class SysConstant {

    private SysConstant() {
        throw new AssertionError("No SysConstant instances for you!");
    }

    /**
     * debug prop key
     */
    public static final String GATEWAY_DEBUG_KEY = "spring.application.debug";

    /**
     * service id prop key
     */
    public static final String GATEWAY_ID_KEY = "spring.application.id";
}
