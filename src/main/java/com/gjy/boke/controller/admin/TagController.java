package com.gjy.boke.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjy.boke.entity.Tag;
import com.gjy.boke.service.Impl.TagServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/7 18:32
 * @Version 1.0
 */
@Controller
@RequestMapping("admin")
public class TagController {
    @Resource
    TagServiceImpl tagService;

    @GetMapping("tags")
    public String s_tags(){
        return "/admin/tags";
    }

    @RequestMapping("index")
    public String index(){
        return "/admin/index";
    }

    /**
     * 跳转到新增标签
     * @return
     */
    @RequestMapping("AddTag")
    public String AddTag(Model model){
        model.addAttribute("tag",new Tag());
        return "/admin/tag-input";
    }

    /**
     * 分页查看所有标签
     * @return
     */
    @GetMapping("TagsList")
    public String TagsList(Model model, @RequestParam(defaultValue = "1",name="pageNum") Integer pageNum){
        //pageNum为前端传递过来的分页后的“当前处在第几页”，默认在第一页
        //pageSize，表示每页存放的记录数
        PageHelper.startPage(pageNum,5);
        List<Tag> tags = tagService.GetTagList();
        //获取分页结果对象
        PageInfo<Tag> tagsPageInfo = new PageInfo<>(tags);
        model.addAttribute("tagpage", tagsPageInfo);
        return "/admin/tags";
    }

    /**
     * 保存新增的标签
     */
    @PostMapping("saveTag")
    public String saveTag(@RequestParam String tagname,RedirectAttributes attributes){
        //新增标签前应该先判断该标签名是否已经存在，如果存在则不允许新增
        Tag tag = tagService.getTagByName(tagname);
        if(tag==null){
            //如果查询的结果为空，说明可以新增该标签
            int i = tagService.SaveTag(tagname);
            attributes.addFlashAttribute("message","新增成功");
            return "redirect:/admin/TagsList";

        }else{
            attributes.addFlashAttribute("message","新增失败，不可以重复添加标签名");
            return "redirect:/admin/AddTag";
        }

    }

    /**
     * 根据前端传递的id，删除标签
     * @return
     */
    @GetMapping("tag/{id}/tagDelete")
    public String deleteTagById(@PathVariable Long id,RedirectAttributes attributes){
        int i = tagService.deleteTag(id);
        if(i==1){
            attributes.addFlashAttribute("message","删除成功");
            return "redirect:/admin/TagsList";
        }else{
            attributes.addFlashAttribute("message","删除失败");
            return "redirect:/admin/TagsList";
        }

    }

    //点击编辑，按钮，携带要修改的分类id,跳转到tag-input页面，进行编辑
    @GetMapping("tag/{id}/tagEdit")
    public String tagEdit(@PathVariable  Long id,Model model){
        System.out.println("到达");
        //根据前端获取的编号id，查询到对应的分类名称，填充到input页面
        model.addAttribute("tag",tagService.getTagById(id));
        return "/admin/tag-input";
    }

    /**
     * 编辑修改标签名称
     */
    @PostMapping("saveTag/{id}")
    public String  TagEdit(@PathVariable Long id,@RequestParam String tagname,RedirectAttributes attributes){
        //提交修改标签名前应该判断该新标签名是否已经存在
        Tag tagByName = tagService.getTagByName(tagname);
        if(tagByName!=null){
            //tagById不为空，说明该标签名已经存在，则不能使用该标签名
            attributes.addFlashAttribute("message","编辑失败，该标签名已经存在");
            return "redirect:/admin/AddTag";
        }else{
            //该标签名不存在，根据标签id修改标签名
            int i = tagService.updateTagNameById(id, tagname);
            attributes.addFlashAttribute("message","操作成功");
            return "redirect:/admin/TagsList";
        }
    }
}
