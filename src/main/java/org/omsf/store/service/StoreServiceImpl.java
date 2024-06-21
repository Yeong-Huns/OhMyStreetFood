package org.omsf.store.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
//@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public List<Store> getStoreByposition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store getStoreByNo(int storeNo) {
		Store store = storeRepository.getStoreByNo(storeNo).orElseThrow(() -> new NoSuchElementException("해당하는 상점을 찾을 수 없습니다"));
		return store;
	}

	@Override
	public int createStore(Store store) {
		storeRepository.createStore(store);
		int storeNo = store.getStoreNo();
		return storeNo;
	}

	@Override
	public void updateStore(Store store) {
		storeRepository.updateStore(store);
	}

	@Override
	public void deleteStore(int storeNo) {
		storeRepository.deleteStore(storeNo);
	}

	@Override
	public Store updateTotalReview(Store store) {
		int reviewCount = store.getTotalReview() + 1;
		store.setTotalReview(reviewCount++);
		return store;
	}

	@Override
	public Store updateTotalRating(Store store) {
		double totalRating = store.getTotalRating() + 1;
		// 업데이트 할 때 리뷰랑 평군점수 업데이트. 삭제할때는?
		return store;
	}

	@Override
	public Store updateLikes(Store store) {
		int reviewCount = store.getTotalReview() + 1;
		store.setTotalReview(reviewCount++);
		return store;
	}

	@Override
	public int UploadImage(ArrayList<MultipartFile> files, int storeNo) {
		String savedFileName = "";
//		String uploadPath = servletContext.getRealPath("/uploaded_files/");
		String uploadPath = "/temp/uploaded_files/";
		int photoNo = 0;
		ArrayList<String> originalFileNameList = new ArrayList<String>();
        for(MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            originalFileNameList.add(originalFileName);
            
            UUID uuid = UUID.randomUUID();
            // 확장자 추출
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            savedFileName = uuid.toString() + fileExtension;

            File file1 = new File(uploadPath + savedFileName);
           
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            //서버로 전송
            try {
				file.transferTo(file1);
			} catch (IllegalStateException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
            
            Photo photo = Photo.builder()
      			  .contentType(file.getContentType())
      			  .fileSize(file.getSize())
      			  .picture(uploadPath + savedFileName)
      			  .storeNo(storeNo)
      			  .build();
           
            storeRepository.createPhoto(photo);
           photoNo = photo.getPhotoNo();
        }
        
        return photoNo;
	}

}
