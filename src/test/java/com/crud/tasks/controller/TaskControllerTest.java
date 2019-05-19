package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTasksEmpty() throws Exception {

        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        List<Task> taskFromDb = new ArrayList<>();

        when(service.getAllTasks()).thenReturn(taskFromDb);
        when(taskMapper.mapToTaskDtoList(taskFromDb)).thenReturn(taskDtoList);

        mockMvc.perform(get("/v1/tasks/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$",hasSize(0)));

    }

    @Test
    public void testGetTasks() throws Exception {

        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L,"Test Name","Test Content"));
        List<Task> taskFromDb = new ArrayList<>();
        taskFromDb.add(new Task(1L,"Test Name","Test Content"));

        when(service.getAllTasks()).thenReturn(taskFromDb);
        when(taskMapper.mapToTaskDtoList(taskFromDb)).thenReturn(taskDtoList);

        mockMvc.perform(get("/v1/tasks/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0]id",is(1)))
                .andExpect(jsonPath("$[0].title",is("Test Name")))
                .andExpect(jsonPath("$[0].content",is("Test Content")));

    }

    @Test
    public void testDeleteTask() throws Exception {

        Long taskId = 1L;

        mockMvc.perform(delete("/v1/tasks/deleteTask")
                .contentType(MediaType.APPLICATION_JSON)
                 .param("taskId","1")).andExpect(status().isOk());

    }

    @Test
    public void testGetTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L,"Test Name","Test Content");
        Task task = new Task(1L,"Test Name","Test Content");

        Optional<Task> returnedTask = Optional.of(task);

        Long taskId = 1L;

        when(service.getTask(anyLong())).thenReturn(returnedTask);
        when(taskMapper.mapToTaskDto(returnedTask.get())).thenReturn(taskDto);


        mockMvc.perform(get("/v1/tasks/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId","1"))

        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.title",is("Test Name")))
        .andExpect(jsonPath("$.content",is("Test Content")));

    }

    @Test
    public void testUpdateTask() throws Exception {

        TaskDto taskDto = new TaskDto(23L,"Updated Name","Updated Content");

        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDto);

        Gson g = new Gson();

        String jsonContent = g.toJson(taskDto);

        mockMvc.perform(put("/v1/tasks/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
        .andExpect(jsonPath("$.id",is(23)))
        .andExpect(jsonPath("$.title",is("Updated Name")))
        .andExpect(jsonPath("$.content",is("Updated Content")));

    }

    @Test
    public void testCreateTask() throws Exception {


        TaskDto taskDto = new TaskDto(323L,"New Task Name","New Task Content");
        Task task = new Task(323L,"New Task Name","New Task Content");

        when(service.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);

        Gson g = new Gson();

        String jsonContent = g.toJson(taskDto);

        mockMvc.perform(post("/v1/tasks/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());




    }

}