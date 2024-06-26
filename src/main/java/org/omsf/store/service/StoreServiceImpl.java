package org.omsf.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private StoreService storeService;
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
	public List<Store> getPopularStores() {
		return storeRepository.getPopularStores();
	}
	
	@Override
	public Store getStoreByNo(int storeNo) {
		Store store = storeRepository.getStoreByNo(storeNo).orElseThrow(() -> new NoSuchElementException("해당하는 상점을 찾을 수 없습니다"));
		return store;
	}
	
	@Override
	public List<Store> searchByKeyword(String keyword, String orderType, int offset, int limit) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("orderType", orderType);
	    params.put("offset", offset);
	    params.put("limit", limit);
	    
	    return storeRepository.searchByKeyword(params);
	}

	
	// yunbin
	@Override
	public String getStoreNameByStoreNo(int storeNo) {
		return storeRepository.getStoreNameByStoreNo(storeNo);
	}
	
	@Override
	public List<Map<String, Object>> getStoreList(StorePagination page) {
		List<Store> stores =  storeRepository.getStoreList(page);
		List<Map<String, Object>> storeWithPhotoList = new ArrayList<>();
	    for (Store store : stores) {
	        Map<String, Object> storeWithPhotoMap = new HashMap<>();
	        storeWithPhotoMap.put("store", store);
	        if (store.getPicture() != null) {
	            Photo photo = storeService.getPhotoByPhotoNo(store.getPicture());
	            storeWithPhotoMap.put("photo", photo);
	        } else {
	            storeWithPhotoMap.put("photo", null);
	        }
	        storeWithPhotoList.add(storeWithPhotoMap);
	    }
	    return storeWithPhotoList; 
	}

	// leejongseop
	@Override
	public List<Store> getStoresByPosition(String position) {
		String[] locationArray = position.split(" ");
		// 서울일때는 "구", 지방일때는 "시" 기준
		String pos = locationArray[1];
		return storeRepository.getStoresByPosition(pos);
	}

	@Override
	public Photo getPhotoByPhotoNo(int photoNo) {
		return storeRepository.getPhotoByPhotoNo(photoNo);
	}

	@Override
	public List<Photo> getStorePhotos(int storeNo) {
		Store store = storeService.getStoreByNo(storeNo);
		return storeRepository.getStorePhotos(store);	
	}
}
