package com.algamoney.event.listener;

import com.algamoney.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent event) {
        HttpServletResponse httpServletResponse = event.getResponse();
        Long codigo = event.getCodigo();
        adicionarLocationHeader(httpServletResponse, codigo);
    }

    private void adicionarLocationHeader(HttpServletResponse httpServletResponse, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}").buildAndExpand(codigo).toUri();
        httpServletResponse.setHeader("Location", uri.toASCIIString());
    }
}
