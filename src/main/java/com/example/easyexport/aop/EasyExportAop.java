package com.example.easyexport.aop;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.easyexport.anotation.EasyExport;
import com.example.easyexport.model.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;

@Aspect
@Slf4j
@Component
public class EasyExportAop {


    @Around(value = "@annotation(com.example.easyexport.anotation.EasyExport)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Around advice - Before");
        Object[] args = joinPoint.getArgs();
        log.info("args:{}",args);
        Object result = joinPoint.proceed(args);
        if (args == null){
           return result;
        }
        Collection<?> list =  (Collection<?>) result;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EasyExport easyExportAnnotation = method.getAnnotation(EasyExport.class);
        HttpServletResponse response = (HttpServletResponse) args[0];
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(easyExportAnnotation.fileName(), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        if (args.length > 1 && easyExportAnnotation.pageQuery()){
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(),  easyExportAnnotation.clazz()).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("test").build();
            PageInfo pageInfo = (PageInfo) args[1];
            Integer page = pageInfo.getPage();
            while (!CollectionUtils.isEmpty(list)){
                excelWriter.write(list,writeSheet);
                ++page;
                pageInfo.setPage(page);
                args[1] = pageInfo;
                list = (Collection<?>) joinPoint.proceed(args);
            }
            excelWriter.finish();
            return result;
        }
        EasyExcel.write(response.getOutputStream(), easyExportAnnotation.clazz()).sheet("test").doWrite((Collection<?>) result);
        log.info("Around advice - After");
        return result;
    }
}
