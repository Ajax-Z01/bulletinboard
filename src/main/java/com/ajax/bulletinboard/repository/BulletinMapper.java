package com.ajax.bulletinboard.repository;

import com.ajax.bulletinboard.model.Bulletin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BulletinMapper {

    @Select("SELECT * FROM bulletins WHERE deleted = false ORDER BY created_at DESC")
    List<Bulletin> findAll();

    @Select("SELECT * FROM bulletins WHERE id = #{id} AND deleted = false")
    Bulletin findById(Long id);

    @Insert("INSERT INTO bulletins (title, author, content, password, created_at) VALUES " +
        "(#{title}, #{author}, #{content}, #{password}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Bulletin bulletin);

    @Update("UPDATE bulletins SET title = #{title}, content = #{content}, updated_at = NOW() WHERE id = #{id}")
    void update(Bulletin bulletin);

    @Update("UPDATE bulletins SET deleted = true WHERE id = #{id}")
    void softDelete(Long id);

    @Update("UPDATE bulletins SET views = views + 1 WHERE id = #{id} AND deleted = false")
    void incrementViews(Long id);
}
