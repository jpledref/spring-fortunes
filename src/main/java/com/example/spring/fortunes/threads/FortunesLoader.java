package com.example.spring.fortunes.threads;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
		
		Resource resourceFile = new ClassPathResource("/input");		
		File file=null;
		try {
			file = resourceFile.getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		if(file!=null){					
			ret.addAll(browseFile(file));
		}
		LOG.info("Number of entries: {}",ret.size());
		fortuneDAO.save(ret);
	}

	public void setFortuneDAO(FortuneDAO fortuneDAO) {
		this.fortuneDAO = fortuneDAO;
	}	
	
	private List<Fortune> browseFile(File f){
		List<Fortune> ret=new ArrayList<Fortune>();
		if(f==null)return ret;
		
		if(f.isFile()){
			ret.addAll(parseFile(f));
		}else {
			List<Fortune> tmp=new ArrayList<Fortune>();
			Arrays.stream(f.listFiles()).forEach(inputFile->{
				tmp.addAll(browseFile(inputFile));				
			});	
			ret.addAll(tmp);
		}
		
		return ret;
	}
	
	private List<Fortune> parseFile(File f){
		List<Fortune> ret=new ArrayList<Fortune>();
		List<String> excluded=Arrays.asList("Makefile.am", "Makefile.common", "Makefile.in");		
		if(excluded.contains(f.getName()))return ret;
		
		String data="";
		try {
			data = FileUtils.readFileToString(f,"latin1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] arr=data.trim().split("%");
		Arrays.stream(arr).forEach(s->{
				ret.add(new Fortune(s.trim()));
		});
		return ret;
	}
	
}
