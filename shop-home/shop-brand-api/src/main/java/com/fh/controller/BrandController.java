package com.fh.controller;

import com.fh.service.IBrandService;
import com.fh.commons.ServerResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("brand")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
public class BrandController {
    @Resource(name = "brandService")
    private IBrandService brandService;

    @GetMapping("/{typeId}")
    private ServerResult findBrandList(@PathVariable("typeId") Integer typeId){
        return brandService.findBrandAll(typeId);
    }
}
