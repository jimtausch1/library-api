package com.luv2code.library_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.luv2code.library_api.model.AddBookRequest;
import com.luv2code.library_api.service.AdminService;
import com.luv2code.library_api.utils.ExtractJWT;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PutMapping("/secure/increase/book/quantity")
  public void increaseBookQuantity(@RequestHeader(value = "Authorization") String token,
      @RequestParam Long bookId) throws Exception {
    String userEmail = ExtractJWT.emailJWTExtraction(token);
    if (userEmail == null) {
      throw new Exception("Administration page only");
    }
    adminService.increaseBookQuantity(bookId);
  }

  @PutMapping("/secure/decrease/book/quantity")
  public void decreaseBookQuantity(@RequestHeader(value = "Authorization") String token,
      @RequestParam Long bookId) throws Exception {
    String userEmail = ExtractJWT.emailJWTExtraction(token);
    if (userEmail == null) {
      throw new Exception("Administration page only");
    }
    adminService.decreaseBookQuantity(bookId);
  }

  @PostMapping("/secure/add/book")
  public void postBook(@RequestHeader(value = "Authorization") String token,
      @RequestBody AddBookRequest addBookRequest) throws Exception {
    String userEmail = ExtractJWT.emailJWTExtraction(token);
    if (userEmail == null) {
      throw new Exception("Administration page only");
    }
    adminService.postBook(addBookRequest);
  }

  @DeleteMapping("/secure/delete/book")
  public void deleteBook(@RequestHeader(value = "Authorization") String token,
      @RequestParam Long bookId) throws Exception {
    String userEmail = ExtractJWT.emailJWTExtraction(token);
    if (userEmail == null) {
      throw new Exception("Administration page only");
    }
    adminService.deleteBook(bookId);
  }

}
