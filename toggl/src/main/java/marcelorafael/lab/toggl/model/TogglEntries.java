package marcelorafael.lab.toggl.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

@Data
public class TogglEntries {
	private Long id;
	@SerializedName("workspace_id")
	private Long workspaceId;
	@SerializedName("project_id")
	private Long projectId;
	@SerializedName("task_id")
	private Long taskId;
	private Boolean billable;
	private Date start;
	private Date stop;
	private Integer duration;
	private String description;
	private Object tags;
	@SerializedName("tag_ids")
	private Object tagIds;
	@SerializedName("server_deleted_at")
	private Date serverDeletedAt;
	@SerializedName("user_id")
	private Long userId;
	private Long uid;
	private Long wid;
	private Long pid;
}
