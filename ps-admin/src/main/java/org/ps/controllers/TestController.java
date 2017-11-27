package org.ps.controllers;

import com.alibaba.fastjson.JSON;
import org.ps.domain.AnalysisParam;
import org.ps.domain.UnCompressParam;
import org.ps.domain.Task;
import org.ps.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/test")
public class TestController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(name = "/add/{scheduer}/{monitor}/{executor}/{paramString}")
    public String addTask(
            @PathVariable(name = "scheduer") final String scheduer,
            @PathVariable(name = "monitor") final String monitor,
            @PathVariable(name = "executor") final String executor,
            @PathVariable(name = "paramString") final String paramString) {

        Task task = new Task();
        task.setId(System.currentTimeMillis() + "");
        task.setExecutor(executor);
        task.setMonitor(monitor);
        task.setScheduler(scheduer);
        task.setCreateTime(System.currentTimeMillis());
        task.setSort(1);
        task.setParamString(paramString);
        taskService.addTask(task);
        return  "success";
    }

    @RequestMapping("/addDemo02")
    public String test(){
        UnCompressParam param = new UnCompressParam();
        param.setSrcFilePath("/home/neusoft/ps-job/data/src");
        param.setDestFilePath("/home/neusoft/ps-job/data/temp");
        return this.addTask("unPackageScheduler","unPackageMonitor","unPackageExecutor", JSON.toJSONString(param));
    }

    @RequestMapping("/addDemo03")
    public String test01(){
        AnalysisParam param = new AnalysisParam();
        param.setSrcDir("/home/neusoft/ps-job/data/temp");
        return this.addTask("analysisScheduler",",analysisMonitor","analysisExecutor",JSON.toJSONString(param));
    }

    @RequestMapping("/addDemo04")
    public String test02(){
        return this.addTask("demoScheduler","demoMonitor","demoExecutor","");
    }

}
