package marcelorafael.lab.jira.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
	private String self;
	private String accountId;
	private String displayName;
	private Boolean active;
}
