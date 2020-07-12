package com.example.sw2.controller;

import com.example.sw2.utils.ExceptionView;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


@ControllerAdvice
public class MainController {
    @ExceptionHandler(value = MultipartException.class)
    public ModelAndView erroHandler11(Exception ex, HandlerMethod hm){
        return errorHandler(ex,hm);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ModelAndView erroHandler12(Exception ex, HandlerMethod hm){
        return errorHandler(ex,hm);
    }

    @ExceptionHandler(value = SizeLimitExceededException.class)
    public ModelAndView erroHandler13(Exception ex, HandlerMethod hm){
        return errorHandler(ex,hm);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ModelAndView erroHandler14(Exception ex, HandlerMethod hm){
        return errorHandler(ex,hm);
    }


    private ModelAndView errorHandler(Exception ex, HandlerMethod hm) {
        System.out.println("IN !!!!!!");
        String targetView;
        if (hm != null && hm.hasMethodAnnotation(ExceptionView.class)) {
            targetView = Objects.requireNonNull(hm.getMethodAnnotation(ExceptionView.class)).getValue();
        } else {
            targetView = "redirect:/"; // kind of a fallback
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName(targetView);
        mv.addObject("msgError", "El archivo es mayor al tamaño máximo (2MB)");
        return mv;
    }

}
