package com.sm.app.admin.web.resource.usecase;

import com.sm.app.admin.config.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.sm.app.admin.web.resource.dto.ResourceCreateRequestDto;
import com.sm.app.admin.web.resource.dto.ResourceModifyRequestDto;
import com.sm.app.admin.web.resource.dto.ResourceResponseDto;
import com.sm.app.admin.web.resource.dto.ResourcesResponseDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.repository.query.ResourceExistsDto;
import com.sm.app.domainrdb.core.resource.repository.resource.ResourceRepository;
import com.sm.app.domainrdb.core.resource.repository.resourcerole.ResourceRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ResourceUseCase {

    private final ResourceRepository resourceRepository;
    private final ResourceRoleRepository resourceRoleRepository;
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
//    private final RabbitMqScSecService rabbitMqScSecService;

    public Page<ResourcesResponseDto> getResources(SiteTypeEnum siteType, Pageable pageable, String search) {
        List<Resource> resources = resourceRepository.searchResources(siteType, pageable, search);
        Long totalCount = resourceRepository.countResources(siteType, search);

        return new PageImpl<>(resources.stream().map(ResourcesResponseDto::from).toList(), pageable, totalCount);
    }

    @Transactional
    public void createResource(ResourceCreateRequestDto resourceCreateRequestDto) {
        ResourceExistsDto resourceExistsDto = ResourceExistsDto.of(null, resourceCreateRequestDto.getSiteType(),
                resourceCreateRequestDto.getResourcePattern(), resourceCreateRequestDto.getHttpMethod());

        boolean isExistsResource = resourceRepository.existsResource(resourceExistsDto);
        if (isExistsResource) {
            throw new ServiceException("이미 등록된 리소스입니다.");
        }

        resourceRepository.save(resourceCreateRequestDto.toResource());
    }

    public ResourceResponseDto getResource(Long id) {
        return ResourceResponseDto.from(resourceRepository.findById(id)
                .orElseThrow(() -> new ServiceException("리소스를 찾을 수 없습니다.")));
    }

    @Transactional
    public void modifyResource(Long id, ResourceModifyRequestDto resourceModifyRequestDto) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ServiceException("리소스를 찾을 수 없습니다."));

        ResourceExistsDto resourceExistsDto = ResourceExistsDto.of(resource.getId(), resource.getSiteType(),
                resourceModifyRequestDto.getResourcePattern(), resourceModifyRequestDto.getHttpMethod());

        boolean isExistsResource = resourceRepository.existsResource(resourceExistsDto);
        if (isExistsResource) {
            throw new ServiceException("이미 등록된 리소스입니다.");
        }

        resource.modifyResource(resourceModifyRequestDto.getResourceName(), resourceModifyRequestDto.getResourcePattern(),
                resourceModifyRequestDto.getHttpMethod(), resourceModifyRequestDto.getOrderNum());

        reloadSecurityMetadataSource(resource.getSiteType());
    }

    @Transactional
    public void deleteResource(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ServiceException("리소스를 찾을 수 없습니다."));

        boolean isExists = resourceRoleRepository.existsByResource(resource);
        if (isExists) {
            throw new ServiceException("권한에 할당된 리소스가 존재합니다.");
        }

        resourceRepository.delete(resource);
    }

    // 시큐리티 리소스 설정을 다시 로드
    private void reloadSecurityMetadataSource(SiteTypeEnum siteTypeEnum) {
        if (SiteTypeEnum.ADMIN.equals(siteTypeEnum)) {
            urlFilterInvocationSecurityMetadataSource.reload();
        } else if(SiteTypeEnum.USER.equals(siteTypeEnum)) {
            /*rabbitMqScSecService.sendReqReload(RabbitMqMessageDto.builder()
                    .message(SecReloadEnum.RESOURCE_RELOAD.name())
                    .build());*/
        }
    }
}
