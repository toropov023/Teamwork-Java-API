package ca.toropov.api.teamwork.command;

import ca.toropov.api.teamwork.util.RequestMethod;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import lombok.Builder;

/**
 * Author: toropov
 * Date: 1/26/2019
 */
@Builder
public class CreateTaskCommand implements Command {
    private String taskListId;
    @Builder.Default
    private String taskName = "New task";
    private String description;
    private String dueDate;
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

    @Override
    public String getJson() {
        JsonStringWriter json = JsonWriter.string()
                .object()
                .object("todo-item")
                .value("content", taskName);
        if (description != null) {
            json.value("description", description);
        }
        if (startDate != null) {
            json.value("start-date", startDate);
        }
        if (dueDate != null) {
            json.value("due-date", dueDate);
        }
        json.end().end();
        return json.done();
    }
}
