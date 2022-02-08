package org.company.myapp.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {


		//root-context.xml 에 빈생성
		@Autowired
		private String saveDir;
		
		//공통모듈
		@Override
		public String fileUpload(MultipartFile file) throws Exception {
			//파일을 폴더에 저장하고 파일명을 리턴
			//업로드 파일이름 : 시스템의 시간 + 실제파일이름
			String originfilename = file.getOriginalFilename();
			//파일이 없다면 return
			if (originfilename.equals("")) return "";
			
			String filename = System.currentTimeMillis()+"_"+ originfilename;
			//업로드 경로(폴더명), 파일명
			File f = new File(saveDir, filename);;
			file.transferTo(f); //업로드경로에 파일 저장

			return filename;
		
	}
	
}
