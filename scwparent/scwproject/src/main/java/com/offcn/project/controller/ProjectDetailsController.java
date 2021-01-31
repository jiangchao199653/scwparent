package com.offcn.project.controller;


import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.po.TReturn;
import com.offcn.project.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "项目信息")
@RestController
@RequestMapping("/project")
public class ProjectDetailsController {

    @Autowired
    private ProjectService projectService;

    @ApiOperation("获取回报详情")
    @GetMapping("/getReturnByRid")
    public AppResponse<TReturn> getReturnByRid(Integer rid){
        System.out.println("3333333333");
        TReturn tReturn = projectService.getReturnByRid(rid);
        return AppResponse.ok(tReturn);
    }


}
