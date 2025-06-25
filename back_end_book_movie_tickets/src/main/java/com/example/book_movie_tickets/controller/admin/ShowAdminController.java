package com.example.book_movie_tickets.controller.admin;

import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.model.Shows;
import com.example.book_movie_tickets.service.impl.IShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Date;

@RestController
@RequestMapping("/api/admin/show")
public class ShowAdminController {

    @Autowired
    private IShowService showService;
    @GetMapping("")
    public ResponseEntity<?> findAllRoom(@RequestParam(required = false)
                                         @DateTimeFormat(pattern = "yyyy-MM-dd")Date date,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "HH:mm") Time time,
                                         @PageableDefault(size = 5) Pageable pageable) {
        Page<Shows> showsPage;
        if (date!=null&&time==null){
            showsPage=showService.searchByDateShowPage(date,pageable);
        }else if (date==null&&time!=null){
            showsPage=showService.searchByStartTimePage(time,pageable);
        }else if (date!=null&&time!=null){
            showsPage=showService.searchByDateAndStartTimePage(date,time,pageable);
        } else {
            showsPage=showService.findAllShowPage(pageable);
        }
        if (!showsPage.isEmpty()){
            return ResponseEntity.ok(showsPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }
    @GetMapping("{id}")
    public ResponseEntity<?> detailShow(@PathVariable("id") Integer id){
        Shows shows = showService.detailShowById(id);
        if (shows!=null){
            return ResponseEntity.ok(shows);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }
    @PostMapping("")
    public ResponseEntity<?> createShow(@RequestBody Shows  shows){
        try {
            showService.createShow(shows);
            return ResponseEntity.ok("Thêm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateShow(@PathVariable("id") int id,
                                        @RequestBody Shows shows){
        try {
            showService.updateShow(id,shows);
            return ResponseEntity.ok("Chỉnh sửa thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteShow(@PathVariable("id") int id){
        try {
            showService.deleteShow(id);
            return ResponseEntity.ok("Xoá thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
