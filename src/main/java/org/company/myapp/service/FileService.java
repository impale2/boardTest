package org.company.myapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	//파일을 업로드하고 파일명을 리턴
	String fileUpload(MultipartFile file) throws Exception;
}
