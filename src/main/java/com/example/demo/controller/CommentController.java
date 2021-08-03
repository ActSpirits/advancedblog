package com.example.demo.controller;

import com.example.demo.bean.Comment;
import com.example.demo.bean.User;
import com.example.demo.service.CommentService;
import com.example.demo.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/listCommentByBlogId")
    @CrossOrigin
    public List<Comment> listCommentByBlogId(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                             @RequestParam("blogId") Integer blogId) {
        return commentService.listCommentByBlogId(blogId);
    }

    @RequestMapping("/insertComment")
    @CrossOrigin
    public String insertComment(@RequestParam("content") String content,
                                @RequestParam("parentId") Integer parentId,
                                @RequestParam("blogId") Integer blogId,
                                HttpServletRequest request) {
        System.out.println(blogId);
        String token = request.getHeader("token");
        System.out.println(JWTUtils.verify(token).getClaims().get("id").asString());
        String id = JWTUtils.verify(token).getClaims().get("id").asString();
        if (parentId == 0) {
            commentService.insertCommentWithoutParentId(content, new Date(), blogId, Integer.parseInt(id));
        } else {
            commentService.insertCommentWithParentId(content, new Date(), blogId, Integer.parseInt(id), parentId);
        }
        return "评论成功!";
    }
}
