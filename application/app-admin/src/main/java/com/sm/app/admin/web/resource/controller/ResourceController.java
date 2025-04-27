package com.sm.app.admin.web.resource.controller;

import com.sm.app.admin.web.resource.dto.ResourceCreateRequestDto;
import com.sm.app.admin.web.resource.dto.ResourceModifyRequestDto;
import com.sm.app.admin.web.resource.dto.ResourcesResponseDto;
import com.sm.app.admin.web.resource.service.ResourceService;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/resources/site-type/{siteType}")
    public String resources(@PathVariable SiteTypeEnum siteType, Model model) {
        model.addAttribute("siteType", siteType);
        return "pages/resource/resources";
    }

    @GetMapping("/api/resources/site-type/{siteType}")
    public ResponseEntity<Page<ResourcesResponseDto>> getResources(@PathVariable SiteTypeEnum siteType,
                                                                   Pageable pageable,
                                                                   @RequestParam(required = false) String search) {
        return ResponseEntity.ok(resourceService.getResources(siteType, pageable, search));
    }

    @GetMapping("/resources/site-type/{siteType}/create")
    public String resourceCreate(@PathVariable SiteTypeEnum siteType, Model model) {
        model.addAttribute("siteType", siteType);
        model.addAttribute("httpMethods", ResourceHttpMethodEnum.values());
        return "pages/resource/create";
    }

    @PostMapping("/api/resources")
    public ResponseEntity<Object> createResource(@RequestBody @Valid ResourceCreateRequestDto resourceCreateRequestDto) {
        resourceService.createResource(resourceCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("resources/{id}")
    public String getResource(@PathVariable Long id, Model model) {
        model.addAttribute("resource", resourceService.getResource(id));
        model.addAttribute("httpMethods", ResourceHttpMethodEnum.values());
        return "pages/resource/modify";
    }

    @PutMapping("/api/resources/{id}")
    public ResponseEntity<Object> modifyResource(@PathVariable Long id,
                                                 @RequestBody @Valid ResourceModifyRequestDto resourceModifyRequestDto) {
        resourceService.modifyResource(id, resourceModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/resources/{id}")
    public ResponseEntity<Object> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.ok().build();
    }
}
