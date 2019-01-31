package ca.toropov.api.teamwork.command;

import ca.toropov.api.teamwork.util.JsonBuild;
import ca.toropov.api.teamwork.util.JsonBuildClass;
import ca.toropov.api.teamwork.util.RequestMethod;
import lombok.Builder;

/**
 * Author: toropov
 * Date: 1/26/2019
 */
@Builder
@JsonBuildClass("todo-item")
public class CreateTaskCommand implements Command {
    @JsonBuild(ignore = true)
    private String taskListId;
    @Builder.Default
    @JsonBuild("content")
    private String taskName = "New task";
    private String description;
    @JsonBuild("due-date")
    private String dueDate;
    @JsonBuild("start-date")
    private String startDate;

    @Override
    public String getRequestUrl() {
        if (taskListId == null) {
            throw new NullPointerException("Tasklist ID cannot be null");
        }
        return "/tasklists/" + taskListId + "/tasks.json";
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
