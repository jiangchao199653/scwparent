package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.constants.ProjectConstant;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.TypeMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public TProjectMapper projectMapper;

    @Autowired
    public TProjectImagesMapper imagesMapper;

    @Autowired
    public TReturnMapper returnMapper;

    @Autowired
    public TProjectTagMapper tProjectTagMapper;

    @Autowired
    public TProjectTypeMapper projectTypeMapper;

    @Autowired
    public TTagMapper tTagMapper;

    @Autowired
    public TTypeMapper tTypeMapper;

    @Override
    public String createProject(Integer mid) {
        //1、制作项目的令牌
        String projectToken = UUID.randomUUID().toString().replace("-","");
        //2、创建redis存储对象格式
        ProjectRedisStorageVo redisStorageVo = new ProjectRedisStorageVo();
        redisStorageVo.setMemberid(mid);
        redisStorageVo.setProjectToken(projectToken);
        //3、存入redis
        stringRedisTemplate.opsForValue().set(ProjectConstant.EMP_PROJECT_PREFIX+projectToken,
                JSON.toJSONString(redisStorageVo));

        return projectToken;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume statusEnume, ProjectRedisStorageVo redisStorageVo) {

        //1、存项目
        TProject project = new TProject();
        BeanUtils.copyProperties(redisStorageVo,project);
        project.setStatus(statusEnume.getCode()+"");
//        project.setCreatedate(new Date());
        projectMapper.insertSelective(project);

        //2、存图片
        TProjectImages headImage = new TProjectImages();
        headImage.setProjectid(project.getId());
        headImage.setImgtype(ProjectImageTypeEnume.HEADER.getCode());
        headImage.setImgurl(redisStorageVo.getHeaderImage());
        imagesMapper.insert(headImage);

        List<String> detailsImage = redisStorageVo.getDetailsImage();
        for (String img : detailsImage) {
            TProjectImages image = new TProjectImages();
            image.setProjectid(project.getId());
            image.setImgtype(ProjectImageTypeEnume.DETAILS.getCode());
            image.setImgurl(img);
            imagesMapper.insert(image);
        }

        //3、存回报
        List<TReturn> returnList = redisStorageVo.getProjectReturns();
        for (TReturn tReturn : returnList) {
            tReturn.setProjectid(project.getId());
            returnMapper.insert(tReturn);
        }

        //4、存标签
        List<Integer> tagList = redisStorageVo.getTagids();
        for (Integer tagTd : tagList) {
            TProjectTag tag = new TProjectTag();
            tag.setProjectid(project.getId());
            tag.setTagid(tagTd);
            tProjectTagMapper.insert(tag);
        }

        //5、存类型
        List<Integer> tTypeList = redisStorageVo.getTypeids();
        for (Integer typeId : tTypeList) {
            TProjectType projectType = new TProjectType();
            projectType.setProjectid(project.getId());
            projectType.setTypeid(typeId);
            projectTypeMapper.insert(projectType);
        }

        //6、清空redis
        stringRedisTemplate.delete(ProjectConstant.EMP_PROJECT_PREFIX+redisStorageVo.getProjectToken());

    }

    @Override
    public List<TReturn> findReturns(Integer pid) {
        TReturnExample example = new TReturnExample();
        TReturnExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(pid);

        return returnMapper.selectByExample(example);
    }

    @Override
    public List<TProject> getProAll() {
        return projectMapper.selectByExample(null);
    }

    @Override
    public List<TProjectImages> getProImages(Integer pid) {
        TProjectImagesExample example = new TProjectImagesExample();
        TProjectImagesExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(pid);
        return imagesMapper.selectByExample(example);
    }

    @Override
    public TProject findProjectByPid(Integer pid) {
        return projectMapper.selectByPrimaryKey(pid);
    }

    @Override
    public List<TTag> findTagByPid(Integer pid) {
        TTagExample example = new TTagExample();
        TTagExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return tTagMapper.selectByExample(example);
    }

    @Override
    public List<TType> findTypeByPid(Integer pid) {

        List<TType> tTypeList = new ArrayList<TType>();

        TProjectTypeExample example = new TProjectTypeExample();
        TProjectTypeExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(pid);
        List<TProjectType> projectTypeList = projectTypeMapper.selectByExample(example);

        for (TProjectType projectType : projectTypeList) {
            TType tType = tTypeMapper.selectByPrimaryKey(projectType.getTypeid());
            tTypeList.add(tType);
        }

        return tTypeList;
    }

    @Override
    public TReturn getReturnByRid(Integer rid) {
        return returnMapper.selectByPrimaryKey(rid);
    }
}
