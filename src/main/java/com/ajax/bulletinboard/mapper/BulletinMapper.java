package com.ajax.bulletinboard.mapper;

import com.ajax.bulletinboard.model.Bulletin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BulletinMapper {

    @Select("SELECT * FROM bulletins")
    List<Bulletin> findAll();

    @Select("SELECT * FROM bulletins WHERE id = #{id}")
    Bulletin findById(Long id);

    @Insert("INSERT INTO bulletins (title, content, created_at) VALUES (#{title}, #{content}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Bulletin bulletin);

    @Update("UPDATE bulletins SET title = #{title}, content = #{content} WHERE id = #{id}")
    void update(Bulletin bulletin);

    @Delete("DELETE FROM bulletins WHERE id = #{id}")
    void delete(Long id);
}
