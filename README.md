[![](https://jitpack.io/v/toropov023/Teamwork-Java-API.svg)](https://jitpack.io/#toropov023/Teamwork-Java-API)
# WIP: Teamwork Java API
_A simple teamwork.com Java API_

The project is work in progress, only a few simple API commands have been covered. Contributions are welcomed!

**Minimum Java 8 is required.**
Depends on [lombok](https://github.com/rzwitserloot/lombok) and [nanojson](https://github.com/mmastrac/nanojson)

## Sample usage:
1. Find your Teamwork [API key](https://developer.teamwork.com/projects/finding-your-url-and-api-key/api-key-and-url)
2. Where needed, the `taskListId` can be found from the url of the task list (Ex: ...teamwork.com/#tasklists/**1234567**)
```
TeamworkAPI api = new TeamworkAPI("apiKey", "subdomain");

CreateTaskCommand.builder()
    .taskListId("1234567")
    .taskName("My new task!")
    .dueDate("20190101")
    .build()
    .dispatch(api);

//.dispatch(); Will also work if you're only using 
// one instance of TeamworkAPI

//You can also use TeamworkAPI#dispatch(command);
```
Note: `dispatch()` is executed in sync with your current thread. 
Use appropriate measurements to use this API in async. Ex:
```
new Thread(() -> {
    //Call dispatch() here
}).start();
```
