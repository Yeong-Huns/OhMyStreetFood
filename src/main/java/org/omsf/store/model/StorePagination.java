package org.omsf.store.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class StorePagination {
    private int currPageNo; // 현재 페이지 번호
    private int sizePerPage; // 한 페이지당 보여질 리스트 개수
    private int totalCnt; // 전체 목록 개수

    private String searchType; // 검색 타입 (가게 이름, 지역 등)
    private String keyword; // 키워드
    private String orderType; // 정렬 타입 (인기순, 댓글순 등)
    private String sortOrder; //오름차, 내림차순
    
    private String fromDate; // 날짜 기한 시작
    private String toDate; // 날짜 기한 종료
    
    public int getStartRow() {
        return (currPageNo - 1) * sizePerPage;
    }
    
    public static class StorePaginationBuilder {
        private int currPageNo = 1; 
        private int sizePerPage = 10; 
        private String sortOrder = "DESC"; 

    }
}
