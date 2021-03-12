package com.gjy.boke.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjy.boke.entity.Type;
import com.gjy.boke.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Author GJY
 * @Date 2021/1/4 22:34
 * @Version 1.0
 */
@Controller
@RequestMapping("admin")
public class TypeController {
    @Resource
    private TypeService typeService;

    /**
     * 点击admin下的types下的新增按钮，跳转到新增分类页面
     * @return
     */
    @GetMapping("type/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    /**
     * 新增分类
     * @param name
     * @return
     */
    @PostMapping("saveTypes")
    public String  blog(@RequestParam String name, RedirectAttributes attributes){
        //新增分类之前查看该分类是否已经存在
        Type type = typeService.getTypeByName(name);
        if(type!=null){
            attributes.addFlashAttribute("message","操作失败，不能重复添加该分类");
            //如果查询到的type不为空，说明数据库中已存在该分类名称,则重定向到types页面中，并则给出提示消息
            return "redirect:/admin/type/input";

        }else{

            int i = typeService.saveType(name);

            if(i==1){
                attributes.addFlashAttribute("message","新增成功");
            }else{
                attributes.addFlashAttribute("message","新增失败");
            }
            return "redirect:/admin/types";
        }


    }

    //分页查询
    @GetMapping("types")
    public String list(Model model, @RequestParam(defaultValue = "1",name="pageNum") Integer pageNum) {
        //pageNum为前端传递过来的分页后的“当前处在第几页”，默认在第一页
        //pageSize，表示每页存放的记录数
        PageHelper.startPage(pageNum, 5);
        List<Type> typeList = typeService.getTypeList();
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(typeList);
        model.addAttribute("page", pageInfo);
        return "/admin/types";
    }

    //删除分类
    @GetMapping("types/{id}/delete")
    public String deleteTypeById(@PathVariable Long id,RedirectAttributes attributes) {
        int i = typeService.deleteByName(id);
        if (i == 1) {
            //删除成功
            attributes.addFlashAttribute("message", "删除成功");
        }else{
            //删除失败
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/types";
    }

    //点击编辑，按钮，携带要修改的分类id,跳转到type-input页面，进行编辑
    @GetMapping("types/{id}/edit")
    public String editType0(@PathVariable  Long id,Model model){
        //根据前端获取的编号id，查询到对应的分类名称，填充到input页面
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/type-input";
    }

    /**
     *编辑分类名称（更新）
     * @param id  id为所要修改的分类编号
     * @param name name为前端表单填写新的分类名称
     * @param attributes
     * @return
     */
    @PostMapping("saveTypes/{id}")
    public String updateType(@PathVariable Long id,@RequestParam String name,RedirectAttributes  attributes) {
        //提交表单的时候验证该分类名称是否已经存在
        Type type = typeService.getTypeByName(name);
        if (type == null) {
            //type为空，说明可以使用该分类名称
            int i = typeService.updateTypeById(id, name);
            if(i==1){
                attributes.addFlashAttribute("message", "操作成功");
                return "redirect:/admin/types";
            }else{
                attributes.addFlashAttribute("message", "操作失败");
                return "redirect:/admin/type/input";
            }

        } else {
            attributes.addFlashAttribute("message", "操作失败，该分类已存在");
            //如果查询到的type不为空，说明数据库中已存在该分类名称,则重定向到types页面中，并则给出提示消息
            return "redirect:/admin/type/input";
        }
    }



}
