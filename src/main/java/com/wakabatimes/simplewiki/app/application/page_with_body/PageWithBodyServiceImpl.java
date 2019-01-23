package com.wakabatimes.simplewiki.app.application.page_with_body;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBody;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodyRepository;
import com.wakabatimes.simplewiki.app.domain.model.search.Search;
import com.wakabatimes.simplewiki.app.domain.service.page_with_body.PageWithBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
public class PageWithBodyServiceImpl implements PageWithBodyService{
    @Autowired
    private PageWithBodyRepository pageWithBodyRepository;

    @Override
    public PageWithBodies search(Search search) {
        return pageWithBodyRepository.search(search);
    }

    @Override
    @Async
    public void pdfExport(PageWithBody pageWithBody, HttpServletResponse res) throws IOException {
        String pageName = pageWithBody.getPage().getPageName().getValue();
        String bodyHtml = pageWithBody.getBody().getBodyHtml().getValue();
        String encodedFilename = URLEncoder.encode(pageName + ".pdf", "UTF-8");

        res.reset();
        res.setHeader("Content-Disposition","attachment;filename=\"" + encodedFilename + "\"");

        res.setContentType(MediaType.APPLICATION_PDF_VALUE);
        Pdf pdf = new Pdf();
        pdf.addPageFromString("<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>" + pageName + "</title>" +
                "</head>" +
                "<body class=\"base-body\">" +
                "<div class=\"base-content-article article max\">" + bodyHtml + "</div>" +
                "</body>" +
                "</html>");

        try {
            byte[] output = pdf.getPDF();
            OutputStream out = res.getOutputStream();
            out.write(output);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
