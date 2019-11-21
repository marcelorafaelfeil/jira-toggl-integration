package marcelorafael.lab.jira.payload;

import lombok.Data;

import java.util.List;

@Data
public class CommentPayload {
	private String type;
	private Integer version;
	private List<Comment> comment;

	public CommentPayload(String text) {
		Comment.ContentText content = new Comment.ContentText();
		content.setType("text");
		content.setText(text);

		Comment comment = new Comment();
		comment.setType("paragraph");
		comment.setContent(List.of(content));

		this.type = "doc";
		this.version = 1;
		this.comment = List.of(comment);
	}

	@Data
	public static class Comment {
		private String type;
		List<ContentText> content;

		@Data
		private static class ContentText {
			private String type;
			private String text;
		}
	}
}
