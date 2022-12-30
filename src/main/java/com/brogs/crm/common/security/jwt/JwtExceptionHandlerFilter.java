package com.brogs.crm.common.security.jwt;

import com.brogs.crm.common.exception.BaseException;
import com.brogs.crm.common.exception.ExpiredCredentialsException;
import com.brogs.crm.common.exception.InvalidCredentialsException;
import com.brogs.crm.common.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        try{
            filter.doFilter(request, response);
        } catch (InvalidCredentialsException ex) {
            createCommonErrorResponse(response, ex, HttpServletResponse.SC_OK);
        } catch (ExpiredCredentialsException ex) {
            createCommonErrorResponse(response, ex, HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void createCommonErrorResponse(HttpServletResponse response, BaseException ex, int status) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                                CommonResponse.fail(ex.getMessage(), ex.getErrorCode().toString())
                        ));
        response.setStatus(status);
    }
}