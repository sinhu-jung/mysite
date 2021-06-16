package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;

	public List<GalleryVo> viewPage() {
		return galleryRepository.findAll();
	}

	public void insert(GalleryVo vo) {
		galleryRepository.insert(vo);
	}

	public void delete(Long no) {
		galleryRepository.delete(no);
	}

}
