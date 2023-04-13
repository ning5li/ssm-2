package com.ning.controller;

import com.ning.Service.Impl.CourseServiceImpl;
import com.ning.bean.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;




    //课程列表
    @RequestMapping(value = "/course",method = RequestMethod.GET)
    public String getAllCourses(Model model){

        List<Course> courses = courseService.allCourses();
        model.addAttribute("courses",courses);
        return "course_list";

    }

    //检查课程名是否重复
    @RequestMapping("/check")
    @ResponseBody
    public String validName(String name,Integer id){
        Course course = courseService.selectByName(name);
        if(course==null||(course!=null&&course.getId()==id)){
            return "false";
        }else{
            return "true";
        }

    }

//    添加课程
    @RequestMapping(value = "/toAdd")
    public String toAdd(){
        return "course_add";
    }
    //添加课程
    @RequestMapping(value = "/course" ,method = RequestMethod.POST)
    public String courseAdd(Course course,HttpServletRequest request) throws IOException {
        Course courseByname = courseService.selectByName(course.getName());
        if(courseByname==null){
            courseService.addCourse(course);
//            request.setAttribute("msg","添加失败");
        }
//        else request.setAttribute("msg","添加成功");
        return "redirect:/course";

    }
    //查询课程
    @RequestMapping(value = "/course/{id}",method = RequestMethod.GET)
    public String getCourseById(@PathVariable("id") Integer id, ModelMap model){

        Course course = courseService.selectCourse(id);
        model.addAttribute("change",course);
        return "course_update";

    }
    //更新课程信息
    @RequestMapping(value = "/course",method = RequestMethod.PUT)
    public String courseUpdate(@Param("course") Course course,HttpServletRequest request){
        Course course1 = courseService.selectByName(course.getName());
        if(course1!=null&&course1.getId()!=course.getId()){
//            request.setAttribute("msg","更改失败");


        }
        else{
            courseService.updateCourse(course);
//            request.setAttribute("msg", "更改成功");
        }
        return "redirect:/course";

    }
    //删除课程
    @RequestMapping(value = "/course/{id}",method = RequestMethod.DELETE)
    public String courseDelete(@PathVariable("id") Integer id){

        courseService.deleteCourse(id);
        return "redirect:/course";

    }

}
