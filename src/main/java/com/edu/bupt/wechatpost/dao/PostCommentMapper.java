package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostCommentMapper {
    List<Post> selectPostByOpenId(@Param("openId")String openId);
    List<Post> selectByKeySelective(@Param("searchText")String searchText);
    Integer deletePostByPostId (@Param("pId") Integer pId);
    Integer insertPostSelective(Post post);
    Integer updateFavoriteNum(@Param("pId")Integer pId, @Param("num")Integer num);
    Integer insertComment(Comment comment);
    Integer deleteCommentByCommentId(@Param("cId") Integer cId);
    Comment selectCommentById(@Param("id")Integer id);
    Post selectPostById(@Param("id")Integer id);
}
