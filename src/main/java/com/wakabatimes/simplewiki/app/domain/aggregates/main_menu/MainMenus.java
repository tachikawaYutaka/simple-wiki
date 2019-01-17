package com.wakabatimes.simplewiki.app.domain.aggregates.main_menu;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenus {
    List<MainMenu> mainMenus;

    public MainMenus(){
        mainMenus = new ArrayList<>();
    }

    public MainMenus(List<MainMenu> mainMenus) {
        this.mainMenus = new ArrayList<>(mainMenus);
    }

    /**
     * MainMenu add
     * @throws RuntimeException
     */
    public void add(@NonNull MainMenu mainMenu) {
        this.mainMenus.add(mainMenu);
    }

    /**
     * MainMenu count
     * @return Integer
     */
    public Integer size() {
        return this.mainMenus.size();
    }


    /**
     * get list
     *
     * @return List<MainMenu>
     */
    public List<MainMenu> list() {
        return Collections.unmodifiableList(this.mainMenus);
    }

    /**
     * get response list
     * @return
     */
    public List<MainMenuResponseDto> responseList(){
        List<MainMenuResponseDto> result = new ArrayList<>();
        List<MainMenu> mainMenus = Collections.unmodifiableList(this.mainMenus);
        for(MainMenu mainMenu : mainMenus) {
            MainMenuResponseDto mainMenuResponseDto = new MainMenuResponseDto(mainMenu);
            List<PageResponseDto> pageResponseDtos = new ArrayList<>();
            for(Page page : mainMenu.getPages().list()) {
                PageResponseDto pageResponseDto = new PageResponseDto(page);
                pageResponseDtos.add(pageResponseDto);
            }
            mainMenuResponseDto.setPages(pageResponseDtos);
        }
        return result;
    }
}
