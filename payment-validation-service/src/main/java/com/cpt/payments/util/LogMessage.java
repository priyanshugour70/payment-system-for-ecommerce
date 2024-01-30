package com.cpt.payments.util;

import org.apache.logging.log4j.Logger;

public final class LogMessage {

	private static final ThreadLocal<String> LOG_MESSAGE = new ThreadLocal<>();

	private LogMessage() {

	}

	public static void setLogMessagePrefix(final String logMessagePrefix) {
		LOG_MESSAGE.set(logMessagePrefix + " : ");
	}

	public static void close() {
		LOG_MESSAGE.remove();
	}

	public static void log(final Logger logger, final Object object) {
		logger.info(LOG_MESSAGE.get() + object);
	}

	public static void warn(final Logger logger, final Object object) {
		logger.warn(LOG_MESSAGE.get() + object);
	}

	public static void debug(final Logger logger, final Object object) {
		logger.debug(LOG_MESSAGE.get() + object);
	}

	public static void logException(final Logger logger, final Exception e) {
		logger.error(LOG_MESSAGE.get(), e);
	}

}
