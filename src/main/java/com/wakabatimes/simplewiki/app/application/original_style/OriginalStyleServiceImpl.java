package com.wakabatimes.simplewiki.app.application.original_style;

import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleRepository;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalStyleServiceImpl implements OriginalStyleService{
    @Autowired
    OriginalStyleRepository originalStyleRepository;

    @Override
    public void save(OriginalStyle originalStyle) {
        originalStyleRepository.save(originalStyle);
    }

    @Override
    public void update(OriginalStyle originalStyle) {
        originalStyleRepository.update(originalStyle);
    }

    @Override
    public void delete() {
        originalStyleRepository.delete();
    }

    @Override
    public boolean exist() {
        return originalStyleRepository.exist();
    }

    @Override
    public OriginalStyle get() {
        return originalStyleRepository.get();
    }
}
