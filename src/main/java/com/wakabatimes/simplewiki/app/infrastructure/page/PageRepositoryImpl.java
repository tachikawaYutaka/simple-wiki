package com.wakabatimes.simplewiki.app.infrastructure.page;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.infrastructure.page.dto.PageDto;
import com.wakabatimes.simplewiki.app.infrastructure.page.dto.RelateChildPageToParentPageDto;
import com.wakabatimes.simplewiki.app.infrastructure.page.dto.RelatePageToMenuDto;
import com.wakabatimes.simplewiki.app.infrastructure.page.mapper.PageMapper;
import com.wakabatimes.simplewiki.app.infrastructure.page.mapper.RelateChildPageToParentPageMapper;
import com.wakabatimes.simplewiki.app.infrastructure.page.mapper.RelatePageTpMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PageRepositoryImpl implements PageRepository {
    @Autowired
    PageMapper pageMapper;

    @Autowired
    RelatePageTpMenuMapper relatePageTpMenuMapper;

    @Autowired
    RelateChildPageToParentPageMapper relateChildPageToParentPageMapper;

    @Override
    public void save(Page page, MenuId menuId) {
        PageDto input = new PageDto(page);
        RelatePageToMenuDto pageToMenuDto = new RelatePageToMenuDto(page, menuId);
        List<PageDto> pageDtoList = pageMapper.listByMenuId(menuId.getValue());
        Pages pages = new Pages();
        int i = 0;
        for(PageDto pageDto : pageDtoList) {
            PageId pageId = new PageId(pageDto.getId());
            PageName pageName = new PageName(pageDto.getName());
            PageType pageType = PageType.getById(pageDto.getType());
            Page thisPage = new Page(pageId,pageName,pageType);
            pages.add(thisPage);
            i++;
        }
        if(!pages.containsName(page)) {
            input.setSortNumber(i + 1);
            pageMapper.save(input);
            relatePageTpMenuMapper.save(pageToMenuDto);
        }else {
            throw new RuntimeException("同階層に同じページ名が既に登録されています。");
        }
    }

    @Override
    public void save(Page child, PageId parentId) {
        PageDto input = new PageDto(child);
        RelateChildPageToParentPageDto relateChildPageToParentPageDto = new RelateChildPageToParentPageDto(child,parentId);
        List<PageDto> pageDtoList = pageMapper.listByParentPageId(parentId.getValue());
        Pages pages = new Pages();
        int i = 0;
        for(PageDto pageDto : pageDtoList) {
            PageId pageId = new PageId(pageDto.getId());
            PageName pageName = new PageName(pageDto.getName());
            PageType pageType = PageType.getById(pageDto.getType());
            Page thisPage = new Page(pageId,pageName,pageType);
            pages.add(thisPage);
            i++;
        }
        if(!pages.containsName(child)) {
            input.setSortNumber(i + 1);
            pageMapper.save(input);
            relateChildPageToParentPageMapper.save(relateChildPageToParentPageDto);
        }else {
            throw new RuntimeException("同階層に同じページ名が既に登録されています。");
        }
    }

    @Override
    public void update(Page page, MenuId menuId) {
        PageDto input = new PageDto(page);
        List<PageDto> pageDtoList = pageMapper.listByMenuId(menuId.getValue());
        Pages pages = new Pages();
        for(PageDto pageDto : pageDtoList) {
            PageId pageId = new PageId(pageDto.getId());
            PageName pageName = new PageName(pageDto.getName());
            PageType pageType = PageType.getById(pageDto.getType());
            Page thisPage = new Page(pageId,pageName,pageType);
            pages.add(thisPage);
        }
        if(!pages.containsName(page)) {
            pageMapper.update(input);
        }else {
            throw new RuntimeException("同階層に同じページ名が既に登録されています。");
        }
    }

    @Override
    public void update(Page child, PageId parentId) {
        PageDto input = new PageDto(child);
        List<PageDto> pageDtoList = pageMapper.listByParentPageId(parentId.getValue());
        Pages pages = new Pages();
        for(PageDto pageDto : pageDtoList) {
            PageId pageId = new PageId(pageDto.getId());
            PageName pageName = new PageName(pageDto.getName());
            PageType pageType = PageType.getById(pageDto.getType());
            Page thisPage = new Page(pageId,pageName,pageType);
            pages.add(thisPage);
        }
        if(!pages.containsName(child)) {
            pageMapper.update(input);
        }else {
            throw new RuntimeException("同階層に同じページ名が既に登録されています。");
        }
    }

    @Override
    public void delete(Page page) {
        PageDto input = new PageDto(page);
        pageMapper.delete(input);
    }

    @Override
    public Pages listByMenuId(MenuId menuId) {
        List<PageDto> pageDtoList = pageMapper.listByMenuId(menuId.getValue());
        Pages pages = new Pages();
        for(PageDto pageDto : pageDtoList) {
            PageId pageId = new PageId(pageDto.getId());
            PageName pageName = new PageName(pageDto.getName());
            PageType pageType = PageType.getById(pageDto.getType());
            Page thisPage = new Page(pageId,pageName,pageType);
            pages.add(thisPage);
        }
        return pages;
    }

    @Override
    public Pages listByParentPageId(PageId parentId) {
        List<PageDto> pageDtoList = pageMapper.listByParentPageId(parentId.getValue());
        Pages pages = new Pages();
        for(PageDto pageDto : pageDtoList) {
            PageId pageId = new PageId(pageDto.getId());
            PageName pageName = new PageName(pageDto.getName());
            PageType pageType = PageType.getById(pageDto.getType());
            Page thisPage = new Page(pageId,pageName,pageType);
            pages.add(thisPage);
        }
        return pages;
    }

    @Override
    public Page getByPageId(PageId pageId) {
        PageDto pageDto = new PageDto();
        pageDto.setId(pageId.getValue());
        PageDto result = pageMapper.getByPageId(pageDto);
        PageId thisPageId = new PageId(result.getId());
        PageName thisPageName = new PageName(result.getName());
        PageType thisPageType = PageType.getById(result.getType());
        Page thisPage = new Page(thisPageId,thisPageName,thisPageType);
        return thisPage;
    }

    @Override
    public Page getRootPageByName(PageName pageName) {
        PageDto input = new PageDto();
        input.setName(pageName.getValue());
        PageDto result = pageMapper.getByRootPageByName(input);

        PageId pageId = new PageId(result.getId());
        PageName pageName1 = new PageName(result.getName());
        PageType pageType = PageType.getById(result.getType());
        Page page = new Page(pageId,pageName1,pageType);
        return page;
    }

    @Override
    public Page getPageByParentAndChildName(PageId parentId, PageName pageName) {
        PageDto input = new PageDto();
        input.setId(parentId.getValue());
        input.setName(pageName.getValue());
        PageDto result = pageMapper.getPageByParentAndChildName(input);

        PageId pageId = new PageId(result.getId());
        PageName pageName1 = new PageName(result.getName());
        PageType pageType = PageType.getById(result.getType());
        Page page = new Page(pageId,pageName1,pageType);
        return page;
    }

    @Override
    public Page getHomePage(MenuId menuId) {
        PageDto result = pageMapper.getHomePage(menuId.getValue());

        PageId pageId = new PageId(result.getId());
        PageName pageName1 = new PageName(result.getName());
        PageType pageType = PageType.getById(result.getType());
        Page page = new Page(pageId,pageName1,pageType);
        return page;
    }

    @Override
    public Page getParent(PageId pageId) {
        PageDto input = new PageDto();
        input.setId(pageId.getValue());
        PageDto result = pageMapper.getParent(input);

        PageId pageId1 = new PageId(result.getId());
        PageName pageName1 = new PageName(result.getName());
        PageType pageType = PageType.getById(result.getType());
        Page page = new Page(pageId1,pageName1,pageType);
        return page;
    }
}
