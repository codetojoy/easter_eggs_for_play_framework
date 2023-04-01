package forms;

import play.data.validation.Constraints;

public class PostForm {
    @Constraints.Required
    @Constraints.MinLength(value = 2)
    @Constraints.MaxLength(value = 32)
    private String title;

    @Constraints.Required
    @Constraints.MinLength(value = 6)
    @Constraints.MaxLength(value = 200)
    private String content;

    public PostForm() {} 

    public PostForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
