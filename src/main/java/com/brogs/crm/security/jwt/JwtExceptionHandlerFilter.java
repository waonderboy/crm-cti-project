package com.brogs.crm.security.jwt;

import com.brogs.crm.common.exception.InvalidCredentialsException;
import com.brogs.crm.common.response.CommonResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {

        try{
            filter.doFilter(request, response);
        } catch (InvalidCredentialsException e) {
            ObjectMapper objectMapper = new ObjectMapper();

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter()
                    .write(objectMapper.writeValueAsString(
                            CommonResponse.fail(e.getMessage(), e.getErrorCode().toString())
                            )
                    );
        }
    }
}