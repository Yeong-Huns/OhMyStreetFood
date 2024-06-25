package org.omsf.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
//@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private AmazonS3 s3Client;
	@Value("${aws.bucketname}")
	private String bucketName;
	
//	@Override
//	public List<Store> getStoreByposition(String position) {
//		return storeRepository.getStoreByposition(position);
//	}

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
	public int UploadImage(ArrayList<MultipartFile> files, int storeNo) throws IOException {
		String savedFileName = "";
		String uploadPath = "store/";
		int photoNo = 0;
		
		ArrayList<String> originalFileNameList = new ArrayList<String>();
        for(MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            originalFileNameList.add(originalFileName);
            
            // 확장자 추출
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            UUID uuid = UUID.randomUUID();
            savedFileName = uuid.toString() + fileExtension;
            
            //파일경로: 업로드폴더 + uuid.확장자
    		String filePath = uploadPath + savedFileName;
    		s3Client.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream(), null));
    		String url = s3Client.getUrl(bucketName, filePath).toString();
 
            Photo photo = Photo.builder()
      			  .contentType(file.getContentType())
      			  .fileSize(file.getSize())
      			  .picture(url)
      			  .storeNo(storeNo)
      			  .build();
           
           storeRepository.createPhoto(photo);
           photoNo = photo.getPhotoNo();
        }
        
        return photoNo;
	}
	
	// jaeeun
	@Override
	public int addStore(Store store) {
	    storeRepository.insertStore(store);
	    int storeNo = store.getStoreNo();
		return storeNo;
	}

	@Override
	public List<Store> getAllStores() {
		return storeRepository.getAllStores();
	}
	
	@Override
	public Store getStoreByNo(int storeNo) {
		Store store = storeRepository.getStoreByNo(storeNo).orElseThrow(() -> new NoSuchElementException("해당하는 상점을 찾을 수 없습니다"));
		return store;
	}
	
	// yunbin
	@Override
	public String getStoreNameByStoreNo(int storeNo) {
		return storeRepository.getStoreNameByStoreNo(storeNo);
	}
	
	@Override
	public List<Store> getStoreList(StorePagination page) {
		return storeRepository.getStoreList(page);
	}
}
