package marcelorafael.lab.jiratogglintegration.core;

import lombok.extern.slf4j.Slf4j;
import marcelorafael.lab.jiratogglintegration.utils.TemporalUtil;

import java.time.LocalDateTime;

@Slf4j
public class ExecutionServices {
	public static boolean isToClose = false;
	public static boolean isRequestedToSync = false;
	private static final String REFRESH_TIME = ConfigurationProperties.get("integration.refresh-time");
	private static LocalDateTime TIME_TO_REFRESH = LocalDateTime.now();

	public static boolean isAllowToSync() {
		Boolean isTimeAllow = false;
		Boolean isRequested = false;
		if (TIME_TO_REFRESH.isBefore(LocalDateTime.now())) {
			TIME_TO_REFRESH = TIME_TO_REFRESH.plus(TemporalUtil.getTimeOfUnit(REFRESH_TIME), TemporalUtil.getUnit(REFRESH_TIME));
			isTimeAllow = true;
		}
		if (ExecutionServices.isRequestedToSync) {
			isRequested = true;
			ExecutionServices.isRequestedToSync = false;
		}

		return isTimeAllow || isRequested;
	}

	public static void close() {
		ExecutionServices.isToClose = true;
		System.exit(0);
	}

	public static void sync() {
		log.info("Sincronizando informações.");
		ExecutionServices.isRequestedToSync = true;
	}
}
