package com.wakabatimes.simplewiki.app.domain.service.page_with_body;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBody;
import com.wakabatimes.simplewiki.app.domain.model.search.Search;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface PageWithBodyService {
    /**
     * 検索
     * @param search
     * @return
     */
    PageWithBodies search(Search search);

    /**
     * PDFの出力
     * @param pageWithBody
     * @param res
     */
    void pdfExport(PageWithBody pageWithBody, HttpServletResponse res) throws IOException;
}
