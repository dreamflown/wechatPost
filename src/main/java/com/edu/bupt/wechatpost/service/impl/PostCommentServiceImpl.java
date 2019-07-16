package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.dao.MomentTipMapper;
import com.edu.bupt.wechatpost.dao.PostCommentMapper;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.MomentTip;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Autowired
    private PostCommentMapper postCommentMapper;

    @Autowired
    private MomentTipMapper tipMapper;

    @Override
    public List<Post> findAllPosts(String openId, Integer page) {
        List<Post> posts = postCommentMapper.selectPostByOpenId(openId);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        return posts.subList(left, right);
    }

    @Override
    public List<Post> searchPosts(String searchText, Integer page) {
        List<Post> posts = postCommentMapper.selectByKeySelective(searchText);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        List<Post> myPosts = new ArrayList<>(posts.subList(left, right));
        return myPosts;
    }

    @Override
    public Integer publishPost(Post post) {
        return postCommentMapper.insertPostSelective(post);
    }

    @Override
    public Integer deletePost(Integer pId) {
        return postCommentMapper.deletePostByPostId(pId);
    }



    @Override
    public Integer addComment(Comment comment) {
        postCommentMapper.insertComment(comment);
        return comment.getcId();
    }

    @Override
    public Integer deleteComment(Integer cId) {
        return postCommentMapper.deleteCommentByCommentId(cId);
    }

    public void addTip(String touser, String avator, Integer action_type, Integer action_id){
        MomentTip tip = new MomentTip();
        tip.setTouser(touser);
        tip.setAvator(avator);
        tip.setAction_type(action_type);
        tip.setAction_id(action_id);
        tip.setCreateTime(new Timestamp(System.currentTimeMillis()));
        tipMapper.insert(tip);
    }

    public void deleteTip(String touser, Integer action_id){
        tipMapper.delete(touser, action_id);
    }

    public Post findPost(Integer id){
        return postCommentMapper.selectPostById(id);
    }

    public Comment findComment(Integer id){
        return postCommentMapper.selectCommentById(id);
    }
}
