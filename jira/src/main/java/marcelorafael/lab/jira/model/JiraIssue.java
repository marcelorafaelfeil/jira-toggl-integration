package marcelorafael.lab.jira.model;

import lombok.Data;

@Data
public class JiraIssue {
	private String expand;
	private String id;
	private String self;
	private String key;
	private Object fields;
}
