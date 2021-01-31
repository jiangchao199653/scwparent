package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.constants.ProjectConstant;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectService;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import com.offcn.project.vo.resp.ProjectDetialVo;
import com.offcn.project.vo.resp.ProjectVo;
import com.offcn.util.OssTemplate;
import com.offcn.vo.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = "项目信息")
@RestController
@RequestMapping("/project")
public class ProjectInfoController {

    @Autowired
    private OssTemplate ossTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProjectService projectService;

    @ApiOperation("获取项目类型")
    @GetMapping("/findTypeByPid")
    public AppResponse findTypeByPid(Integer pid){
        List<TType> tTypeList = projectService.findTypeByPid(pid);
        return AppResponse.ok(tTypeList);
    }

    @ApiOperation("获取项目对应的标签")
    @GetMapping("/findTagByPid")
    public AppResponse<List<TTag>> findTagByPid(Integer pid){
        List<TTag> tTagList = projectService.findTagByPid(pid);
        return AppResponse.ok(tTagList);
    }

    @ApiOperation("获取项目详情")
    @GetMapping("/fingProjectByPid")
    public AppResponse<ProjectDetialVo> fingProjectByPid(Integer pid){

        ProjectDetialVo projectDetialVo = new ProjectDetialVo();

        TProject project = projectService.findProjectByPid(pid);
        BeanUtils.copyProperties(project,projectDetialVo);
        //获取图片
        List<TProjectImages> imagesList = projectService.getProImages(project.getId());
        List<String> imageDetailList = new ArrayList<String>();
        for (TProjectImages tProjectImages : imagesList) {
            if(tProjectImages.getImgtype()==0){
                projectDetialVo.setHeadImage(tProjectImages.getImgurl());
            }else{
                imageDetailList.add(tProjectImages.getImgurl());
            }
        }
        projectDetialVo.setDetailImagesList(imagesList);
        //获取回报
        List<TReturn> returnList = projectService.findReturns(project.getId());
        projectDetialVo.setReturnList(returnList);
        return AppResponse.ok(projectDetialVo);
    }

    @ApiOperation("获取所有项目")
    @GetMapping("/getAllProject")
    public AppResponse<List<ProjectVo>> getAllProject(){

        List<ProjectVo> projectVoList = new ArrayList<ProjectVo>();

        List<TProject> projectList = projectService.getProAll();

        for (TProject project : projectList) {

            ProjectVo vo = new ProjectVo();

            BeanUtils.copyProperties(project,vo);

            List<TProjectImages> imagesList = projectService.getProImages(project.getId());
            for (TProjectImages tProjectImages : imagesList) {
                if(tProjectImages.getImgtype() == 0){
                    vo.setHeadImg(tProjectImages.getImgurl());
                    break;
                }
            }

            projectVoList.add(vo);
        }
        return AppResponse.ok(projectVoList);
    }

    @ApiOperation("获取回报列表")
    @GetMapping("/findReturns")
    public AppResponse<List<TReturn>> findReturns(Integer pid){
        List<TReturn> returnList = projectService.findReturns(pid);
        return AppResponse.ok(returnList);
    }

    @ApiOperation("项目初始化第四步 - 保存项目")
    @PostMapping("/submit")
    public AppResponse submit(String accessToken,String projectToken,String ops){

        //1、判断当前登录人是否登录
        String mid = stringRedisTemplate.opsForValue().get(accessToken);
        if(StringUtils.isEmpty(mid)){
            return AppResponse.fail("用户未登陆");
        }

        //2、取项目
        String jsonProject = stringRedisTemplate.opsForValue().get(ProjectConstant.EMP_PROJECT_PREFIX+projectToken);
        if(StringUtils.isEmpty(jsonProject)){
            return AppResponse.fail("项目不存在");
        }
        ProjectRedisStorageVo redisStorageVo = JSON.parseObject(jsonProject,ProjectRedisStorageVo.class);

        if("1".equals(ops)){
           projectService.saveProjectInfo(ProjectStatusEnume.SUBMIT_AUTH,redisStorageVo);
        }else{
            projectService.saveProjectInfo(ProjectStatusEnume.DRAFT,redisStorageVo);
        }

        return AppResponse.ok("保存成功");
    }


    @ApiOperation("项目初始化第三步 - 保存回报列表")
    @PostMapping("/saveReturnInfo")
    public AppResponse saveReturnInfo(@RequestBody List<ProjectReturnVo> returnVoList){
        //1、获取项目token
        ProjectReturnVo returnVo = returnVoList.get(0);
        String projectToken = returnVo.getProjectToken();
        //2、获取redis项目结构对象
        String jsonProject = stringRedisTemplate.opsForValue().get(ProjectConstant.EMP_PROJECT_PREFIX+projectToken);
        ProjectRedisStorageVo redisStorageVo = JSON.parseObject(jsonProject,ProjectRedisStorageVo.class);

        List<TReturn> returnList = new ArrayList<TReturn>();
        //3、将回报列表进行遍历 存入redis
        for (ProjectReturnVo projectReturnVo : returnVoList) {
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(projectReturnVo,tReturn);
            returnList.add(tReturn);
        }
        redisStorageVo.setProjectReturns(returnList);
        //4、存入redis
        stringRedisTemplate.opsForValue().set(ProjectConstant.EMP_PROJECT_PREFIX+projectToken,JSON.toJSONString(redisStorageVo));
        return AppResponse.ok("回报列表保存成功");
    }

    @ApiOperation("项目初始化第二步 - 保存基本信息")
    @PostMapping("/saveBeseInfo")
    public AppResponse saveBaseInfo(ProjectBaseInfoVo baseInfoVo){
        //1、从redis中取出项目框架
        String jsonBase = stringRedisTemplate.opsForValue().get(ProjectConstant.EMP_PROJECT_PREFIX+baseInfoVo.getProjectToken());
        ProjectRedisStorageVo redisStorageVo = JSON.parseObject(jsonBase,ProjectRedisStorageVo.class);
        //2、将用户填写的项目信息同步到 redis项目框架对象中
        BeanUtils.copyProperties(baseInfoVo,redisStorageVo);
        //3、再将redis项目框架对象存入redis
        stringRedisTemplate.opsForValue().set(ProjectConstant.EMP_PROJECT_PREFIX+baseInfoVo.getProjectToken(),JSON.toJSONString(redisStorageVo));
        return AppResponse.ok("保存成功");
    }

    @ApiOperation("项目初始化第一步 - 生成项目框架")
    @GetMapping("/createProject")
    public AppResponse createProject(BaseVo baseVo){
        //1、获取用户的id
        String mid = stringRedisTemplate.opsForValue().get(baseVo.getAccessToken());

        //2、判断字符串对象是否为空
        if(StringUtils.isEmpty(mid)){
            return AppResponse.fail("用户请登录");
        }

        //3、初始化项目
        String projectToken = projectService.createProject(Integer.parseInt(mid));
        return AppResponse.ok(projectToken);
    }


    @ApiOperation("上传")
    @PostMapping("/upload")
    public Map upload(@RequestParam MultipartFile[] files){

        Map map = new HashMap();

        List urlList = new ArrayList();

        for (MultipartFile file : files) {
            try {
                if(!file.isEmpty()){
                    String url = ossTemplate.upload(file.getInputStream(),file.getOriginalFilename());
                    urlList.add(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("文件上传完毕...");

        map.put("urls",urlList);
        return map;
    }

}
