package com.nowcoder.community;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:xiaoyang
 * @Title: LoggerTests
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/14 21:39
 */

public class LoggerTests {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);

    @Test
    public void testLogger() {
        System.out.println(logger.getName());
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }
}
