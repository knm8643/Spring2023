package com.example.yuch_nug.controller;

import com.example.yuch_nug.mapper.NoticeMapper;
import com.example.yuch_nug.mapper.SubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ChungrimiController {

    @Autowired
    private SubscribeMapper subscribeMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "이메일이 비어 있습니다."));
        }

        if (subscribeMapper.emailExists(email) > 0) {
            return ResponseEntity.status(409).body(Map.of("error", "이미 등록된 이메일입니다."));
        }

        subscribeMapper.insertEmail(email);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/notices")
    public ResponseEntity<?> getNotices() {
        try {
            List<Map<String, Object>> notices = noticeMapper.getNoticeList();
            return ResponseEntity.ok(notices);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "공지사항 목록 불러오기 실패"));
        }
    }

    @PostMapping("/notices/{id}/view")
    public ResponseEntity<?> getNotice(@PathVariable int id) {
        try {
            noticeMapper.increaseNoticeViews(id);
            Map<String, Object> notice = noticeMapper.getNoticeById(id);

            if (notice == null) {
                return ResponseEntity.status(404).body(Map.of("error", "공지사항을 찾을 수 없습니다."));
            }

            return ResponseEntity.ok(notice);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "서버 오류 발생"));
        }
    }
}
