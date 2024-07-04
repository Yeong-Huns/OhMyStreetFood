package org.omsf.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.member.model.Member;
import org.omsf.member.service.MemberService;
import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
//@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreService storeService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private AmazonS3 s3Client;
	@Value("${aws.bucketname}")
	private String bucketName;

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
	public int UploadImage(ArrayList<MultipartFile> files, int storeNo, String username) throws IOException {
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
      			  .username(username)
      			  .build();
           
           storeRepository.createPhoto(photo);
           photoNo = photo.getPhotoNo();
        }
        
        return photoNo;
	}
	
	@Override
	public void deleteImage(int PhotoNo) {
		Photo photo = storeService.getPhotoByPhotoNo(PhotoNo);
		String fileName = photo.getPicture();
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		s3Client.deleteObject(new DeleteObjectRequest(bucketName + "/store" , fileName));
		storeRepository.deletePhoto(PhotoNo);
	}
	
	@Override
	public void updatePhotoOrder(List<Integer> photoOrder, int storeNo, String username) {
		
		List<Photo> photos = storeService.getStoreGallery(storeNo);
		if (photoOrder.size() != photos.size()) {
			return;
		}
		for (int i = 0; i < photoOrder.size(); i++) {
	       int photoNo = photoOrder.get(i);
	       Photo photo = photos.get(i);
	       photo.setPhotoNo(photoNo);
	       photo.setUsername(username);
	       storeRepository.updatePhoto(photo); 
	    }
	}
	
	// jaeeun
	@Override
	public List<Store> getPopularStores() {
		return storeRepository.getPopularStores();
	}
	
	@Override
	public Store getStoreByNo(int storeNo) {
		Store store = storeRepository.getStoreByNo(storeNo).orElseThrow(() -> new CustomBaseException(ErrorCode.NOT_FOUND_STORE));
		return store;
	}
	
	@Override
	public List<Store> showStoreList(String keyword, String orderType, Double latitude, Double longitude, int offset, int limit) {
		Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("orderType", orderType);
	    params.put("latitude", latitude);
	    params.put("longitude", longitude);
	    params.put("offset", offset);
	    params.put("limit", limit);
	    
	    return storeRepository.showStoreList(params);
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
	
	// yunbin
//	@Override
//	public String getStoreNameByStoreNo(int storeNo) {
//		return storeRepository.getStoreNameByStoreNo(storeNo);
//	}
	
	@Override
	public void deleteStoreByUsername(String username) {
		storeRepository.deleteStoreByUsername(username);
	}

	@Override
	public List<Store> getStoresByUsername(String username) {
		return storeRepository.getStoresByUsername(username);
	}
	
	// leejongseop
	@Override
	public List<Map<String, Object>> getStoresByPosition(String position) {
		String[] locationArray = position.split(" ");
		// 서울일때는 "구", 지방일때는 "시" 기준
		String pos = locationArray[1];
		return storeRepository.getStoresByPosition(pos);
	}

	@Override
	public Photo getPhotoByPhotoNo(Integer photoNo) {
		if (photoNo == null) {return null;}
 		Photo photo = storeRepository.getPhotoByPhotoNo(photoNo).orElseThrow(() -> new CustomBaseException(ErrorCode.NOT_FOUND_PHOTO));		
		return photo;
	}
	
	//가게의 전체사진
	@Override
	public List<Photo> getStorePhotos(int storeNo) {
		return storeRepository.getStorePhotos(storeNo);	
	}

	//가제의 대표사진 제외
	@Override
	public List<Photo> getStoreGallery(int storeNo) {
		Store store = storeService.getStoreByNo(storeNo);
		return storeRepository.getStoreGallery(store);
	}

	@Override
	public List<Photo> getUpdateStoreGallery(int storeNo, String username) {
		Store store = storeService.getStoreByNo(storeNo);
		String storeUsername = store.getUsername();
		String memberType = null;
		//memberType 확인
		if (storeUsername != null) {
			Member member = (Member) memberService.findByUsername(storeUsername).get();
			memberType = member.getMemberType();
		}
		// 사장이면 모두수정 아니면 내가 올린 사진만
		if (memberType == "owner" && storeUsername == username) {
			return storeRepository.getStoreGallery(store);
		} else {
			List<Photo> storeGallery = storeRepository.getStoreGallery(store);
			storeGallery = storeGallery.stream()
					.filter(photo -> username.equals(photo.getUsername()))
					.collect(Collectors.toList());	
			return storeGallery;
		}

		
	}

	@Override
	public void updatePicture(Store store) {
		storeRepository.updatePicture(store);	
	}

	

}
