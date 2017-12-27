package com.pos.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rrampall on 27/12/17.
 */
@Configuration
public class DozerConfig {

    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapper dozerBean() {
        List<String> mappingFiles = Arrays.asList(
                "dozer-bean-mappings.xml"
        );

        //using custom converters
        //http://dozer.sourceforge.net/documentation/customconverter.html

        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

}
