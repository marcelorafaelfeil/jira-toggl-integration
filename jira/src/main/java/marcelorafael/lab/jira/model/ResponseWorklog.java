package marcelorafael.lab.jira.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseWorklog {
	private Integer startAt;
	private Integer maxResults;
	private Integer total;
	private List<Worklog> worklogs;

	@Data
	public static class Worklog {
		private String self;
		private Author author;
		private Author updateAuthor;
		private String comment;
		private ZonedDateTime updated;
		private Visibility visibility;
		private ZonedDateTime started;
		private String timeSpent;
		private Integer timeSpentSeconds;
		private String id;
		private String issueId;

		@Data
		public static class Visibility {
			private String type;
			private String value;
		}
	}
}
