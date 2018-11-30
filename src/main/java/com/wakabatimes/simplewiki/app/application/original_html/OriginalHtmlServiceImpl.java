package com.wakabatimes.simplewiki.app.application.original_html;

import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlRepository;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalHtmlServiceImpl implements OriginalHtmlService{
@Autowired
    OriginalHtmlRepository originalHtmlRepository;

    @Override
    public void save(OriginalHtml originalHtml) {
        originalHtmlRepository.save(originalHtml);
    }

    @Override
    public void update(OriginalHtml originalHtml) {
        originalHtmlRepository.update(originalHtml);
    }

    @Override
    public void delete() {
        originalHtmlRepository.delete();
    }

    @Override
    public boolean exist() {
        return originalHtmlRepository.exist();
    }

    @Override
    public OriginalHtml get() {
        return originalHtmlRepository.get();
    }
}
