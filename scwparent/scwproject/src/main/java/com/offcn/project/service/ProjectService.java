package com.offcn.project.service;

import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.po.*;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

import javax.swing.text.html.HTML;
import java.util.List;

public interface ProjectService {

    public String createProject(Integer mid);

    public void saveProjectInfo(ProjectStatusEnume statusEnume, ProjectRedisStorageVo redisStorageVo);

    public List<TReturn> findReturns(Integer pid);

    public List<TProject> getProAll();

    public List<TProjectImages> getProImages(Integer pid);

    public TProject findProjectByPid(Integer pid);

    public List<TTag> findTagByPid(Integer pid);

    public List<TType> findTypeByPid(Integer pid);

    public TReturn getReturnByRid(Integer rid);


}
