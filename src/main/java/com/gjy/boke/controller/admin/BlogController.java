package com.gjy.boke.controller.admin;

/**
 * @Author GJY
 * @Date 2021/1/3 20:05
 * @Version 1.0
 */

import com.gjy.boke.entity.Blog;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.entity.Type;
import com.gjy.boke.entity.User;
import com.gjy.boke.service.BlogService;
import com.gjy.boke.service.TagService;
import com.gjy.boke.service.TypeService;
import com.gjy.boke.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private UserService userService;
    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    //查询所有信息
    @GetMapping("/blogList")
    public String GetBlogList(Model model) {
        List<Blog> allBlog = blogService.getAllBlog();
        //获取所有分类名称,必须放在PageHelper.startPage(1,3);语句之前，不然会被分页处理
        List<Type> Alltype = typeService.getTypeList();
        /**
         * 为何此时的分页不起效
         */
       /* PageHelper.startPage(1, 3);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(allBlog);
        System.out.println(blogPageInfo.getPrePage());*/
        model.addAttribute("types", Alltype);
        model.addAttribute("blogs", allBlog);
        return "admin/blogs";
    }

    //按条件查询
    @PostMapping(value = "/blogSearch")
    public String blogSearch(@RequestParam String title, @RequestParam String typeName,
                             @RequestParam String recommend
            , Model model) {

        System.out.println(typeName);
        String recommend0 = recommend.equals("true") ? "on" : null;
        String title0 = title.equals("") ? null : title;
        String typeName0 = typeName.equals("") ? null : typeName;
        List<Blog> blogByTitleAndTypeAndRecommend = blogService.getBlogByTitleAndTypeNameAndRecommend(title0, typeName0,
                recommend0);
        /*不采用分页*/
        /*PageHelper.startPage(1, 3);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogByTitleAndTypeAndRecommend);*/
        model.addAttribute("blogs", blogByTitleAndTypeAndRecommend);
        return "admin/blogs :: bloglist";
    }

    //点击新增，跳转到新增博客页面boke-input.html
    @RequestMapping("/bokeinput")
    public String addboke(Model model) {
        //查询所有标签（可以在新建博文的时候的新建标签或者分类名称）
        List<Type> types = typeService.getTypeList();
        //查询所有分类
        List<Tag> tags = tagService.GetTagList();
        model.addAttribute("tags", tags);
        model.addAttribute("types", types);
        model.addAttribute("blog", new Blog());
        return "admin/boke-input";
    }

    //提交新增博客
    @PostMapping("/addBlog")
    public String AddBlog(Blog blog, @RequestParam() String tagId, HttpSession session,
                          RedirectAttributes attributes) {
        int i = blogService.saveBlog(blog, tagId, session);
        if (i == 1) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/blogList";
    }

    //删除博客
    @GetMapping("/blog/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
        int i = blogService.deleteBlog(id);
        System.out.println("删除啦");
        if (i == 1) {
            attributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/blogList";
    }

    //更新博客，携带待修改的博客id跳转到编辑页面
    @GetMapping("/blog/{id}/edit")
    public String updateBlog(@PathVariable Long id, Model model) {
        //查询所有标签（可以在新建博文的时候的新建标签或者分类名称）
        List<Type> types = typeService.getTypeList();
        //查询所有分类
        List<Tag> tags = tagService.GetTagList();
        model.addAttribute("tags", tags);
        model.addAttribute("types", types);
        model.addAttribute("blog", blogService.getBlogById(id));
        return "admin/boke-input";
    }

    //提交修改
    @PostMapping("/addBlog/{id}")
    public String updateblog(@PathVariable Long id, Blog blog, @RequestParam String tagId,
                             RedirectAttributes attributes,
                             HttpSession session) {
        blog.setId(id);
        blog.setType(typeService.getTypeById(blog.getTypeid()));
        User user = (User) session.getAttribute("user");
        blog.setUserid(user.getId());
        ArrayList<Tag> tags = new ArrayList<>();
        for (String tagid : tagId.split(",")) {
            //没有选中任何一个标签的情况下，跳出当前循环,不进行类型转换
            if (tagid == "") continue;
            tags.add(tagService.getTagById(Long.parseLong(tagid)));
        }

        blog.setTags(tags);
        blog.setTagids(tagId);
        int i = blogService.editBlog(id, blog);
        attributes.addFlashAttribute("message", "更新成功");
        return "redirect:/admin/blogList";
    }

    @RequestMapping("/statistics")
    public String goStatistics(){
        return "/admin/statistics";
    }
}
