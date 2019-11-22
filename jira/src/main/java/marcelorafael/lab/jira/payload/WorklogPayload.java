package marcelorafael.lab.jira.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorklogPayload {
	private Long timeSpentSeconds;
	private WorklogVisibility visibility;
	private String comment;
	private ZonedDateTime started;

	@Data
	public static class WorklogVisibility {
		private String type;
		private String value;
	}
}
