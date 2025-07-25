package com.example.yuch_nug.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

@Mapper
public interface NoticeMapper {
    void increaseNoticeViews(@Param("id") int id);
    Map<String, Object> getNoticeById(@Param("id") int id);
    List<Map<String, Object>> getNoticeList();
}