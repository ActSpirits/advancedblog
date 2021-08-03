package com.example.demo.controller;

import com.example.demo.bean.Tag;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/listTag")
    @CrossOrigin
    public List<Tag> listTag() {
        return tagService.listTag();
    }

    @RequestMapping("/deleteTagById")
    @CrossOrigin
    public String deleteTagById(@RequestParam("id")Integer id) {
        tagService.deleteTagById(id);
        return "删除标签成功!";
    }

    @RequestMapping("/insertTag")
    @CrossOrigin
    public String insertTag(@RequestParam("name")String name) {
        List<String> tagNameList = tagService.listTagName();
        if (tagNameList.contains(name)){
            return "添加失败,该标签已存在!";
        }else {
            tagService.insertOne(name);
            return "添加标签成功!";
        }
    }

    @RequestMapping("/updateTagName")
    @CrossOrigin
    public String updateTagName(@RequestParam("id")Integer id,
                                @RequestParam("name")String name) {
        tagService.updateTagName(id,name);
        return "修改标签成功!";
    }
}
