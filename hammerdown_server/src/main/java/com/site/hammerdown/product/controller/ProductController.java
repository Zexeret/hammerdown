package com.site.hammerdown.product.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping("public/hello")
    public String hello() {
        return "Hello World";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/userrole")
    public String testUser() {
        return "Hello User";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String testAdmin() {
        return "Hello Admin";
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/god")
    public String testSuper() {
        return "Hello Super Admin";
    }
}
