package com.lagou.edu.pojo;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/25
 * @since 1.0.0
 */
public class testlist {

    public static void main(String[] args) {
        List<Resume> resumes = new ArrayList<>();
        List<Users> usersList = new ArrayList<>();
        Resume resume = new Resume();
        resume.setUserId(1L).setIsDefault(1).setEmail("cesahi");
        resumes.add(resume);
        Resume resume2 = new Resume();
        resume2.setUserId(2L).setIsDefault(2).setEmail("cesahi2");
        resumes.add(resume2);
        Resume resume3 = new Resume();
        resume3.setUserId(2L).setIsDefault(3).setEmail("cesahi3");
        resumes.add(resume3);
        Users users = new Users();
        users.setId(1L).setPassword("123456").setUsername("张三");
        usersList.add(users);
        Users users1 = new Users();
        users1.setId(2L).setPassword("123456").setUsername("李四");
        usersList.add(users1);

        //for (int i = 0; i < usersList.size(); i++) {
        //    List<Resume> resumesList = new ArrayList<>();
        //    for (int j = 0; j < resumes.size(); j++) {
        //        if (usersList.get(i).getId()==resumes.get(j).getUserId()){
        //            resumesList.add(resumes.get(j));
        //        }
        //    }
        //    usersList.get(i).setResumeList(resumesList);
        //}

        //Map map = new HashMap<>();
        //
        //for (Users u: usersList
        //     ) {
        //    map.put(u.getId(),u);
        //}
        ////System.out.println(map);
        //for (Resume r: resumes
        //     ) {
        //    if (map.containsKey(r.getUserId())){
        //        Users users2= (Users) map.get(r.getUserId());
        //        if (CollectionUtils.isEmpty(users2.getResumeList())) {
        //            List<Resume> resumesList = new ArrayList<>();
        //            resumesList.add(r);
        //            users2.setResumeList(resumesList);
        //        }else {
        //            users2.getResumeList().add(r);
        //        }
        //
        //    }
        //}
        System.out.println(usersList);
    }

}
