package com.example.spring.fortunes.threads;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.example.spring.fortunes.dao.FortuneDAO;
import com.example.spring.fortunes.models.Fortune;

@Component
@Scope("prototype")
public class FortunesLoader implements Runnable{
	private static final Logger LOG = LoggerFactory.getLogger(FortunesLoader.class);

	@Autowired
    private FortuneDAO fortuneDAO;	
	
	@Override
	public void run() {
		List<Fortune> ret=new ArrayList<Fortune>();		
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();		
		Resource[] resources=null;
		try {
			resources = resolver.getResources("classpath*:input/**/*");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		List<String> excluded=Arrays.asList("Makefile.am", "Makefile.common", "Makefile.in");
		if(resources!=null)
			for (Resource r : resources) {			
				InputStream is;
				String content=null;
				try {
					if(excluded.contains(FilenameUtils.getName(r.getURL().getFile()))) continue;
					
					is = r.getInputStream();
					content = IOUtils.toString(is, "latin1");  					
					String[] arr=content.trim().split("%");
					Arrays.stream(arr).forEach(s->{
							if(s!=null&&!s.trim().equals(""))ret.add(new Fortune(s.trim()));
					});					
					is.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
				
			}	
		
		LOG.info("Number of entries: {}",ret.size());
		fortuneDAO.save(ret);
	}

	public void setFortuneDAO(FortuneDAO fortuneDAO) {
		this.fortuneDAO = fortuneDAO;
	}
}
